package edu.foro.api.domain.topic;

import java.sql.Date;

public record DataDetailTopic (Long id, String tittle, String message, Date create_date, Status status,
                               String login, String course){

    public DataDetailTopic(Topic topic){
        this(topic.getId(),
                topic.getTittle(),
                topic.getMessage(),
                topic.getCreate_date(),
                topic.getStatus(),
                topic.getUser().getLogin(),
                topic.getCourse().getCourse_name());

    }

}
