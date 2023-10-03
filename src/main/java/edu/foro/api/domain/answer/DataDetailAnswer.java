package edu.foro.api.domain.answer;

import edu.foro.api.domain.topic.Status;

public record DataDetailAnswer(Long id, String loginTopic, String tittle, String topic,
                               String loginAnswer, String answer, Boolean solution, Status Status) {

    public DataDetailAnswer(Answer answer){
        this(answer.getId(),
                answer.getTopic().getUser().getLogin(),
                answer.getTopic().getTittle(),
                answer.getTopic().getMessage(),
                answer.getUser().getLogin(),
                answer.getMessage(),
                answer.getSolution(),
                answer.getTopic().getStatus());
    }
}


