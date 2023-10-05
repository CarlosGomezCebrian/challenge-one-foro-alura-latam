package edu.foro.api.domain.course;

import java.sql.Date;

public record DataDetailCourse(Long id, String courseName, Category category, Date createDate, Boolean activated) {

    public DataDetailCourse(Course course){
        this(course.getId(),
                course.getCourse_name(),
                course.getCategory(),
                course.getCreate_date(),
                course.getActivated());
    }
}

