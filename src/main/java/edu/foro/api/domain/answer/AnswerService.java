package edu.foro.api.domain.answer;


import edu.foro.api.domain.topic.Topic;
import edu.foro.api.infra.errors.IntegrityValidity;
import edu.foro.api.domain.user.UserRepository;
import edu.foro.api.domain.topic.TopicRepository;
import edu.foro.api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public DataDetailAnswer setDataRegistrationAnswer(DataRegistrationAnswer dataRegistrationAnswer) {
        // Realiza las validaciones necesarias, por ejemplo, verificar si el usuario y el topic existen
        Optional<User> userOptional = userRepository.findById(dataRegistrationAnswer.user_id());
        Optional<Topic> topicOptional = topicRepository.findById(dataRegistrationAnswer.topic_id());

        if (userOptional.isEmpty()) {
            throw new IntegrityValidity("User with ID " + dataRegistrationAnswer.user_id() + " not found");
        }

        if (topicOptional.isEmpty()) {
            throw  new IntegrityValidity("Topic with ID " + dataRegistrationAnswer.topic_id() + " not found");
        }
        User user = userOptional.get();
        Topic topic = topicOptional.get();
        Topic topicStatus = topicRepository.getReferenceById(dataRegistrationAnswer.topic_id());
        topicStatus.updateStatusWhitResponse();
        Answer answer = new Answer(dataRegistrationAnswer, user, topic);
        answerRepository.save(answer);
        return new DataDetailAnswer(answer);  }



    public Page<DataDetailAnswer> listarActivated(Pageable pageable) {
        return answerRepository.findByActivatedTrue(pageable).map(DataDetailAnswer::new);
    }

    public Page<DataDetailAnswer> listarResolved(Pageable pageable) {
        return answerRepository.findBySolutionTrue(pageable).map(DataDetailAnswer::new);
    }

    @Transactional
    public void cancel(DelateAnswerData delateAnswerData) {
        Optional<Answer> optionalAnswer = answerRepository.findById(delateAnswerData.id());

        if(optionalAnswer.isEmpty()){
            throw new IntegrityValidity("Answer with ID " + delateAnswerData.id() + " not found");
        }
        Answer answer = answerRepository.getReferenceById(delateAnswerData.id());
        answer.desactivateAnswer();
    }


}



