package edu.foro.api.domain.topic;

import java.sql.Date;

public record ResponseInfo(String answerUser, String answerMessage,  Date answerDate) {
}
