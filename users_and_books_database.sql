drop database if exists OOP_Library;

create database OOP_Library;
use OOP_Library;

drop table if exists users;
create table users (
	user_id int not null auto_increment,
    user_name varchar(255),
    user_phone varchar(255),
    user_password varchar(255),
    user_admin boolean,
    primary key (user_id)
);

drop table if exists books;
create table books (
	book_id int not null auto_increment,
    book_name varchar(255),
    book_author varchar(255),
    book_publication_year int,
    book_borrowed bool,
    borrowed_date Date,
    return_date Date,
    primary key (book_id)
);

insert into users (user_name, user_phone, user_password, user_admin)
values 
	('Huy', '0965425660', '27042005QuocHuy@@', true),
    ('Hoàng', '0835817188', '02052005Hoang@', true);


select * from users;