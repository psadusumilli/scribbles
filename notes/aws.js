

1) Login into AWS console https://console.aws.amazon.com
2) Launch a EC2 instance using the classic wizard, selected AWS linux packaged with mysql, psql,python and ruby.
3) Generate a key-pair in the wizard itself, that key pair is dedicatd that ec2. 
4) Wait for the instance to launch and be online
5) Connect via SSH
	5.1) download the ec1.pem to ~/.ssh from "Key Pairs" menu
	5.2) from laptop terminal 

		vchakrav@T520-VCHAKRAN ~/.ssh
		$ ssh -i ./ec1.pem ec2-user@ec2-75-101-207-165.compute-1.amazonaws.com
		The authenticity of host 'ec2-75-101-207-165.compute-1.amazonaws.com (75.101.207.165)' can't be established.
		RSA key fingerprint is 64:2b:f7:10:f1:20:56:1c:ce:15:e4:23:59:3b:6e:aa.
		Are you sure you want to continue connecting (yes/no)? yes
		Warning: Permanently added 'ec2-75-101-207-165.compute-1.amazonaws.com,75.101.207.165' (RSA) to the list of known hosts.

		       __|  __|_  )
		       _|  (     /   Amazon Linux AMI
		      ___|\___|___|

		https://aws.amazon.com/amazon-linux-ami/2012.09-release-notes/
		There are 10 security update(s) out of 15 total update(s) available
		Run "sudo yum update" to apply all updates.
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ ls
		[ec2-user@domU-12-31-39-09-55-A0 ~]$

6) Run preliminary checks
	6.1) SPACE 
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ df -h
		Filesystem            Size  Used Avail Use% Mounted on
		/dev/xvda1            7.9G  952M  6.9G  12% /
		tmpfs                 298M     0  298M   0% /dev/shm	
	6.2) ROOT ACCESS 
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ sudo su
		[root@domU-12-31-39-09-55-A0 ec2-user]#	
    6.3) JAVA
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ java -version
		java version "1.6.0_24"
		OpenJDK Runtime Environment (IcedTea6 1.11.8) (amazon-56.1.11.8.51.amzn1-x86_64)
		OpenJDK 64-Bit Server VM (build 20.0-b12, mixed mode)
	6.4) RUBY
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ ruby -version
		ruby 1.8.7 (2012-10-12 patchlevel 371) [x86_64-linux]

7) 	Install software
	7.1) TOMCAT
		[ec2-user@domU-12-31-39-09-55-A0 tools]$ wget http://apache.mirrors.pair.com/tomcat/tomcat-7/v7.0.37/bin/apache-tomcat-7.0.37.tar.gz
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ tar -xzvf apache-tomcat-7.0.37.tar.gz
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ sh apache-tomcat-7.0.37/bin/startup.sh	
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ curl localhost:8080 , will get you index.html
		But 8080 will not be accessible from browser


	7.2) HTTPD
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ sudo yum install httpd 
		[ec2-user@domU-12-31-39-09-55-A0 ~]$ sudo service httpd start
		Starting httpd:                                            [  OK  ]
		http://http://ec2-75-101-207-165.compute-1.amazonaws.com/ will give apache index package	


