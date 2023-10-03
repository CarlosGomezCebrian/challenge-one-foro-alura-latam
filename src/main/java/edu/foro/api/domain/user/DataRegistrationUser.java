package edu.foro.api.domain.user;


import edu.foro.api.domain.course.Category;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DataRegistrationUser(
        @NotBlank
        String login,
        @NotBlank
        String password_hash,
        @NotBlank
        String first_name,
        @NotBlank
        String last_name,
        @NotBlank
        @Email
        String email,
        String image_url,
        @NotNull
        Category category) {
}
