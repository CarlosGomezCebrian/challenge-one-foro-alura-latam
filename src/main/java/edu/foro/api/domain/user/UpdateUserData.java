package edu.foro.api.domain.user;

import edu.foro.api.domain.course.Category;
import jakarta.validation.constraints.NotNull;

public record UpdateUserData(@NotNull Long id, String login, String password_hash,
                             String first_name, String last_name, String email, String image_url, Category category) {
}


