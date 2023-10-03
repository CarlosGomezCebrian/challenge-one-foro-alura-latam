CREATE TABLE topics (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tittle VARCHAR(100) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    status VARCHAR(50),
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    activated TINYINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_topics_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_topics_course_id FOREIGN KEY (course_id) REFERENCES courses (id)

);