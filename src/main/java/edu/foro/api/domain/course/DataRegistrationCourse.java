package edu.foro.api.domain.course;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DataRegistrationCourse(
        @NotBlank
        String course_name,
        @NotNull(message = "Category is required")
        Category category) {
}
