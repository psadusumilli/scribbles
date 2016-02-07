POSTGRESQL:
-----------*
history: Came from 'Ingres'->'Postgres'->'PostgreSQL'(supporting sql, prev QUEL a proprietary language)

overview:




















































puppet file from motech:
-------------------------*
class postgres ( $postgresUser, $postgresPassword, $postgresMachine, $postgresMaster, $postgresSlave, $os, $wordsize, $changeDefaultEncodingToUTF8) {

    $allPacks = [ "postgresql91", "postgresql91-server", "postgresql91-libs", "postgresql91-contrib", "postgresql91-devel"]

    file{"/tmp/postgres-repo.rpm" :
        ensure      => present,
        source      => "puppet:///modules/postgres/pgdg-${os}-9.1-4-${wordsize}.noarch.rpm",
    }
    #run the rpm package
    exec { "run_postgres_repo" :
        provider    => "shell",
        command     => "rpm -i /tmp/postgres-repo.rpm",
        creates     => "/etc/yum.repos.d/pgdg-91-centos.repo",
        require     => File["/tmp/postgres-repo.rpm"],
        onlyif      => "test `rpm -qa postgres | wc -l` -eq 0",
    }
    package { "postgres_packs" :
        name        => $allPacks,
        ensure      => "present",
        require     => Exec["run_postgres_repo"],
    }
    #create user postgres
    user { "${postgresUser}" :
        ensure      => present,
        shell       => "/bin/bash",
        home        => "/home/$postgresUser",
        password    => $postgresPassword,
        require     => Exec["run_postgres_repo"],
        managehome => true,
    }
    #copy the tempplated init.d file
    file { "/etc/init.d/postgresql-9.1" :
            ensure      => present,
            content     => template("postgres/postgres-init.d"),
            mode        =>  777,
            group       => "root",
            owner       => "root",
            require     => Package["postgres_packs"],
    }
    #cerate data dirs
    file { "/usr/local/pgsql/" :
        ensure      => "directory",
        owner       => "${postgresUser}",
    }
    file { "/usr/local/pgsql/data":
        ensure      => "directory",
        require     => File["/usr/local/pgsql/"],
        owner       => "${postgresUser}",
    }
    #initialise database
    exec { "initdb":
        command     => "/usr/pgsql-9.1/bin/initdb -D /usr/local/pgsql/data",
        user        => "${postgresUser}",
        require     => [File["/usr/local/pgsql/data"], Package["postgres_packs"]],
        provider    => "shell",
        onlyif      => "test ! -f /usr/local/pgsql/data/PG_VERSION",
    }
    #start server
    exec { "start-server":
        command     => "/usr/pgsql-9.1/bin/postgres -D /usr/local/pgsql/data &",
        user        => "${postgresUser}",
        require     => [Exec["initdb"], Exec["add_to_path"]],
    }
    if $changeDefaultEncodingToUTF8 == "true" {
        exec { "postgres-utf8-encoding":
            command     => "/usr/pgsql-9.1/bin/psql -U ${postgresUser} < /tmp/postgres-utf8-encoding.sql",
            user        => "${motechUser}",
            require     => File["/tmp/postgres-utf8-encoding.sql"]
        }

        file { "/tmp/postgres-utf8-encoding.sql" :
            require     => Exec["start-server"],
            content     => template("postgres/postgres-utf8-encoding.sql"),
            owner       => "${motechUser}",
            group       => "${motechUser}",
            mode        =>  764
        }
    }
    file { "/etc/init.d/postgresql":
        ensure      => "link",
        target      => "/etc/init.d/postgresql-9.1",
    }
    #back existing conf files
    exec{"backup_conf":
        cwd         => "/usr/local/pgsql/data/",
        command     => "mv postgresql.conf postgresql.conf.backup && mv pg_hba.conf pg_hba.conf.backup",
        user        => "${postgresUser}",
        require     => Exec["start-server"],
    }

    exec{"add_to_path":
        command     => "echo \"export PATH=\$PATH:/usr/pgsql-9.1/bin/\" > /etc/profile.d/repmgr.sh && source /etc/profile.d/repmgr.sh",
        require     => Package["postgres_packs"],
        onlyif      => "test ! -f  /etc/profile.d/repmgr.sh",
    }
    #decide based on master/slave
    case $postgresMachine {
        master:{
            file {"/usr/local/pgsql/data/pg_hba.conf":
                content     => template("postgres/master_pg_hba.erb"),
                owner       => "${postgresUser}",
                group       => "${postgresUser}",
                mode        => 600,
                require     => Exec["backup_conf"],
            }
            file {"/usr/local/pgsql/data/postgresql.conf":
                content     => template("postgres/master_postgresql.erb"),
                owner       => "${postgresUser}",
                group       => "${postgresUser}",
                mode        => 600,
                require     => Exec["backup_conf"],
            }
        }
        slave:{
            file {"/usr/local/pgsql/data/pg_hba.conf":
                content     => template("postgres/slave_pg_hba.erb"),
                owner       => "${postgresUser}",
                group       => "${postgresUser}",
                mode        => 600,
                require     => Exec["backup_conf"],
            }

            file {"/usr/local/pgsql/data/postgresql.conf":
                content     => template("postgres/slave_postgresql.erb"),
                owner       => "${postgresUser}",
                group       => "${postgresUser}",
                mode        => 600,
                require     => Exec["backup_conf"],
            }
            
            file {"/usr/local/pgsql/data/recovery.conf":
                content     => template("postgres/slave_recovery.erb"),
                owner       => "${postgresUser}",
                group       => "${postgresUser}",
                mode        => 600,
            }
        }
    }
-----------------------------------------------------------------------------------------*    
'master_pg_hba.erb'
# TYPE  DATABASE        USER            ADDRESS                 	METHOD
# "local" is for Unix domain socket connections only
local   all             all                                     	trust
# IPv4 local connections:
host    all             all             127.0.0.1/32            	trust
host    all             all             <%= postgresSlave %>/32		trust
host    all             all             <%= postgresMaster %>/32	trust
# IPv6 local connections:
host    all             all             ::1/128                 	trust
# Allow replication connections from localhost, by a user with the replication privilege
host    replication     all             <%= postgresSlave %>/32  	trust

'slave_pg_hba.erb'
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local" is for Unix domain socket connections only
local   all             all                                     trust
# IPv4 local connections:
host    all             all             127.0.0.1/32            trust
host    all             all             <%= postgresSlave %>/32	trust
host    all             all             <%= postgresMaster %>/32	trust
# IPv6 local connections:
host    all             all             ::1/128                 trust

'slave_recovery.erb'
standby_mode = 'on'
primary_conninfo = 'host=<%= postgresMaster %>'

'master_postgresql.conf' 
listen_address = # make sure we're listening as appropriate
wal_level = hot_standby
max_wal_senders = 3
checkpoint_segments = 8    
wal_keep_segments = 8 
'slave_postgresql.conf'
wal_level = hot_standby
max_wal_senders = 3
checkpoint_segments = 8    
wal_keep_segments = 8 
hot_standby = on
-----------------------------------------------------------------------------------------*  