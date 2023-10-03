create table courses(

       id bigint not null auto_increment,
       course_name varchar(100) not null unique,
       category varchar(30) not null,
       create_date timestamp,
       activated TINYINT not null,

       primary key(id)
);