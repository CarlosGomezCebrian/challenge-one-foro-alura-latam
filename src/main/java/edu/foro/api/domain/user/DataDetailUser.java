package edu.foro.api.domain.user;

import edu.foro.api.domain.course.Category;

import java.sql.Date;

public record DataDetailUser(Long id, String login, String firstName, String lastName, String email,
                             Category category, Date createDate, Boolean activated) {

    public DataDetailUser(User user){
        this(user.getId(),
                user.getLogin(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getEmail(),
                user.getCategory(),
                user.getCreate_date(),
                user.getActivated());

    }
}



