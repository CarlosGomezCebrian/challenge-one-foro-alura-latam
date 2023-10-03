package edu.foro.api.domain.topic;


import edu.foro.api.domain.course.Course;
import edu.foro.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "topics")
@Entity(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tittle;
    private String message;
    private Date create_date;
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    private Boolean activated;


    public Topic(DataRegistrationTopic dataRegistrationTopic, User user, Course course ) {
        this.tittle = dataRegistrationTopic.tittle();
        this.message = dataRegistrationTopic.message();
        this.create_date = new Date(System.currentTimeMillis());
        this.status = Status.NO_RESPONSE; // Asumiendo que Status.NO_RESPONSE es una constante
        this.user = user;
        this.course = course;
        this.activated = true;
    }

    public void desactivateTopic() {
        this.activated = false;
        this.status = Status.CANCEL;
    }
    public void updateStatusWhitResponse() {
        this.status = Status.WHIT_RESPONSE;
    }

    public void markAsSolved() {
        this.status = Status.RESOLVED;
    }
}
