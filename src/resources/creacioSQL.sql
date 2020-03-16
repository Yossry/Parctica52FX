

--CREATE TABLE users (id SERIAL,nom varchar,cognom varchar ,email varchar,username varchar,password varchar,level int,connected boolean);

--ALTER TABLE users ADD COLUMN nick varchar(10);


  -- insert into users(nom,cognom,email,username,password,level,connected) values ('Josep M','Olmos','olmosmoog@gmail.com','olmos','olmos',10,true);
  --  insert into users(nom,cognom,email,username,password,level,connected) values ('William','Areas','will@gmail.com','william','bbmas',9,false);
  --  insert into users(nom,cognom,email,username,password,level,connected) values ('Roser','Olmos','roser@gmail.com','roser','olmos',5,true);
  --  insert into users(nom,cognom,email,username,password,level,connected) values ('a','Olmos','roser@gmail.com','a','a',5,true);
--update users set nick = 'Allmix' where username='olmos';
--update users set nick = 'WillDeluxe' where username='william';
--update users set nick = 'Blancaneus' where username='roser';
--update users set nick = 'ABCNDEF' where username='a';