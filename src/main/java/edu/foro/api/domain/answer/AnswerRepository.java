package edu.foro.api.domain.answer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByActivatedTrue(Pageable pageable);


    List<Answer> findByTopicId(Long topicId);

    Page<Answer> findBySolutionTrue(Pageable pageable);
}
