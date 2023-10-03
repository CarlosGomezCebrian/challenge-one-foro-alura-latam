create table answers(

       id bigint not null auto_increment,
       message varchar(1000)not null,
       create_date timestamp not null,
       solution TINYINT not null,
       user_id bigint not null,
       topic_id bigint not null,
       activated TINYINT not null,
       primary key(id),

           constraint fk_answers_user_id foreign key(user_id) references users(id),
           constraint fk_answers_topic_id foreign key(topic_id) references topics (id)

);