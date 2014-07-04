create sequence if not exists card_id_seq start with 1 increment by 1;
create table if not exists cards(id integer default nextval('card_id_seq') primary key, title varchar(150) not null, summary varchar(400) not null, startBy timestamp, endBy timestamp);
create table if not exists tags(id integer primary key, name varchar(150) not null,);

