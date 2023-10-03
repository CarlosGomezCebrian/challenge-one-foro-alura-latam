package edu.foro.api.domain.course;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "courses")
@Entity(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String course_name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Date create_date;
    private Boolean activated;


    public Course(DataRegistrationCourse dataRegistrationCourse) {
        this.course_name = dataRegistrationCourse.course_name();
        this.category = dataRegistrationCourse.category();
        this.create_date = new Date(System.currentTimeMillis());
        this.activated = true;
    }

    public void inactiveUser() {
        this.activated = false;
    }
    public void activeUser() {
        this.activated = true;
    }
}
