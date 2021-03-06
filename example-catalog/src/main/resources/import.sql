insert into artists(id,age,gender,name) values (1,39,0,'John Mayer');
insert into artists(id,age,gender,name) values (2,27,1,'Taylor Swift');
insert into artists(id,age,gender,name) values (3,29,0,'Kendrick Lamar');
insert into artists(id,age,gender,name) values (4,35,0,'Lorde');

insert into songs(id,artist_id,title,length) VALUES (1,1,'Stop this train', 300000);
insert into songs(id,artist_id,title,length) VALUES (2,1,'Neon', 300000);
insert into songs(id,artist_id,title,length) VALUES (3,1,'Free Fallin', 300000);
insert into songs(id,artist_id,title,length) VALUES (4,2,'Shake It Off', 300000);
insert into songs(id,artist_id,title,length) VALUES (5,2,'Dear John', 300000);
insert into songs(id,artist_id,title,length) VALUES (6,3,'DNA', 300000);
insert into songs(id,artist_id,title,length) VALUES (7,4,'Royals', 300000);
insert into songs(id,artist_id,title,length) VALUES (8,3,'Blacker the Berry', 300000);

insert into events(id,type,name, date) values(1,0,'John Mayer Tour', '2017-08-03 19:00:00');
insert into events(id,type,name, date) values(2,1,'Coachella', '2017-04-13 09:00:00');

insert into events_artists(event_id, artist_id) values(1,1);
insert into events_artists(event_id, artist_id) values(2,2);
insert into events_artists(event_id, artist_id) values(2,3);
insert into events_artists(event_id, artist_id) values(2,4);