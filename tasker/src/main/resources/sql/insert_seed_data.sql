insert into cards(title,summary,startBy,endBy) values ('card-1','summary-1','2014-07-01 12:00:00','2014-07-05 12:00:00');
insert into cards(title,summary,startBy,endBy) values ('card-2','summary-2','2014-07-02 12:00:00','2014-07-06 12:00:00');
insert into cards(title,summary,startBy,endBy) values ('card-3','summary-3','2014-07-03 12:00:00','2014-07-07 12:00:00');
insert into cards(title,summary,startBy,endBy) values ('card-4','summary-4','2014-07-04 12:00:00','2014-07-08 12:00:00');
insert into cards(title,summary,startBy,endBy) values ('card-5','summary-5','2014-07-05 12:00:00','2014-07-09 12:00:00');

insert into tasks(card,description,status) values(1,'task-1->card-1','complete');
insert into tasks(card,description,status) values(1,'task-2->card-1','progress');
insert into tasks(card,description,status) values(3,'task-1->card-3','complete');
insert into tasks(card,description,status) values(3,'task-1->card-3','park');
insert into tasks(card,description,status) values(4,'task-4->card-3','progress');
insert into tasks(card,description,status) values(5,'task-5->card-3','invalid');

insert into files(card,path) values(1,'/home/vijayrc/projs/VRC5/scribbles/tasker/files/notes.txt');

insert into users(name,password,roles) values ('stan','428e626f22d0273bb2741c8747418eaff1828bcd397fe7d00f3ec30d4519d95d','viewer');
insert into users(name,password,roles) values ('eric','234d6d31ecb9d31204f97fa13cf7c5af2dd45a1bdb862311e3ac259e98e8f796','viewer,creator');
insert into users(name,password,roles) values ('kenny','5ddad0e9200110840b9a010ef0c1207f2e043ccf808714d198abab7e03498d23','viewer,creator,deleter');
insert into users(name,password,roles) values ('kyle','bd153e22ad1d9d05ca680b4b0631d4c5a2da2a0ea1ac82b45cffd9c68301c27c','admin');