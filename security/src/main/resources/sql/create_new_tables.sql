create sequence if not exists users_id_seq start with 1 increment by 1;

create table if not exists users(id integer default nextval('users_id_seq') primary key, name varchar(150) not null, password varchar(400) not null);
