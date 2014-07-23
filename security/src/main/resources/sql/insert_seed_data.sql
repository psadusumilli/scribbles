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