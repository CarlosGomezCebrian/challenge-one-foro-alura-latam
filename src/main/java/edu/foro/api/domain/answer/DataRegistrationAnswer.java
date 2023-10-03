package edu.foro.api.domain.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistrationAnswer(
        @NotBlank
        String message,
        @NotNull
        Long user_id,
        @NotNull
        Long topic_id) {
}
