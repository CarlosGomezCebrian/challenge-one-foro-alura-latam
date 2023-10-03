package edu.foro.api.domain.topic;

import jakarta.validation.constraints.NotNull;

public record DeleteTopicData(@NotNull Long id) {
}
