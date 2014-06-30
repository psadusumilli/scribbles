create table if not exists tasks(
    id char(32) primary key,
    title varchar(150) not null,
    summary varchar(400) not null,
    startBy timestamp,
    endBy timestamp
);

create table if not exists tags(
    id char(32) primary key,
    name varchar(150) not null,
);

