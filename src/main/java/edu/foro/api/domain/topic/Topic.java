package edu.foro.api.domain.topic;


import edu.foro.api.domain.answer.Answer;
import edu.foro.api.domain.course.Course;
import edu.foro.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


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
    private Date resolved_date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    private Boolean activated;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Answer> answers;


    public Topic(DataRegistrationTopic dataRegistrationTopic, User user, Course course ) {
        this.tittle = dataRegistrationTopic.tittle();
        this.message = dataRegistrationTopic.message();
        this.create_date = new Date(System.currentTimeMillis());
        this.status = Status.NO_RESPONSE;
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

    public void markAsResolved() {

        this.status = Status.RESOLVED;
        this.resolved_date = new Date(System.currentTimeMillis());
    }


}
