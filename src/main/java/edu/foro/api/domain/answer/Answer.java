package edu.foro.api.domain.answer;


import edu.foro.api.domain.topic.Topic;
import edu.foro.api.domain.user.User;
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
@Table(name = "answers")
@Entity(name = "Answer")
public class Answer {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String message;
    private Date create_date;
    private Boolean solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private Boolean activated;


    public Answer(DataRegistrationAnswer dataRegistrationAnswer, User user, Topic topic) {

        this.message = dataRegistrationAnswer.message();
        this.create_date = new Date(System.currentTimeMillis());
        this.solution = false;
        this.user = user; // Asigna la instancia de User proporcionada
        this.topic = topic; // Asigna la instancia de Course proporcionada
        this.activated = true;
    }

    public void markAsSolved() {
        this.solution = true;
    }

    public void desactivateAnswer() {
        this.activated = false;
    }
}
