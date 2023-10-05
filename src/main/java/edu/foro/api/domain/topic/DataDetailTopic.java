package edu.foro.api.domain.topic;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.foro.api.domain.answer.Answer;

public record DataDetailTopic(Long id, String topicUser, String course, String tittle, String message, Date createDate, Status status,
                                List<ResponseInfo> responses ) {
    public DataDetailTopic(Topic topic) {
        this(topic.getId(),
                topic.getUser().getLogin(),
                topic.getCourse().getCourse_name(),
                topic.getTittle(),
                topic.getMessage(),
                topic.getCreate_date(),
                topic.getStatus(),
                topic.getAnswers().stream()
                        .map(answer -> new ResponseInfo(
                                answer.getUser().getLogin(),
                                answer.getMessage(),
                                answer.getCreate_date()
                        ))
                        .collect(Collectors.toList()));
    }


}




