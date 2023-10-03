create table users(

       id bigint not null auto_increment,
       login varchar(100) not null unique,
       password_hash varchar(100) not null,
       first_name varchar(50) not null,
       last_name varchar(50) not null,
       email varchar(100) not null unique,
       image_url varchar(100),
       category varchar(100) not null,
       create_date timestamp not null,
       activated TINYINT not null,

       primary key(id)

);