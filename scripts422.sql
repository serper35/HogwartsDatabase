create table car (
id serial primary key,
brand text,
model text,
price serial)

create table person (
id serial primary key,
name text,
age serial,
licence boolean,
car_id integer references car (id)
