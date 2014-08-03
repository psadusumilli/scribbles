create sequence if not exists card_id_seq start with 1 increment by 1;
create sequence if not exists task_id_seq start with 1 increment by 1;
create sequence if not exists file_id_seq start with 1 increment by 1;
create sequence if not exists users_id_seq start with 1 increment by 1;

create table if not exists users(id integer default nextval('users_id_seq') primary key, name varchar(100) not null, password varchar(300) not null, roles varchar(500));
create table if not exists cards(id integer default nextval('card_id_seq') primary key, title varchar(150) not null, summary varchar(400) not null, startBy timestamp, endBy timestamp);
create table if not exists tags(id integer primary key, name varchar(150) not null);
create table if not exists tasks(id integer default nextval('task_id_seq') primary key,card integer, description varchar(450) not null, status char(20) not null);
create table if not exists files(id integer default nextval('file_id_seq') primary key,card integer, path varchar(500) not null);
