package edu.foro.api.domain.topic;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DataRegistrationTopic(
        @NotBlank
        String tittle,
        @NotBlank
        String message,
        @NotNull
        Long user_id,
        @NotNull
        Long course_id) {
}



