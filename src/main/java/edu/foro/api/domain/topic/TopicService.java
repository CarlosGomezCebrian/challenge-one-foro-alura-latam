package edu.foro.api.domain.topic;

import edu.foro.api.domain.answer.Answer;
import edu.foro.api.domain.answer.AnswerRepository;
import edu.foro.api.domain.course.Course;
import edu.foro.api.domain.course.CourseRepository;
import edu.foro.api.infra.errors.IntegrityValidity;
import edu.foro.api.domain.user.UserRepository;
import edu.foro.api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public DataDetailTopic setDataRegistrationTopic(DataRegistrationTopic dataRegistrationTopic) {
        Optional<User> userOptional = userRepository.findById(dataRegistrationTopic.user_id());
        Optional<Course> courseOptional = courseRepository.findById(dataRegistrationTopic.course_id());

        if (userOptional.isEmpty()) {
            throw new IntegrityValidity("El id " + dataRegistrationTopic.user_id() + " del usuario no fue encontrado");
        }

        if (courseOptional.isEmpty()) {
            throw  new IntegrityValidity("El id " + dataRegistrationTopic.course_id() + " del curso no fue encontrado");
        }

        User user = userOptional.get();
        Course course = courseOptional.get();

        Topic topic;
        topic = new Topic(dataRegistrationTopic, user, course);
        topicRepository.save(topic);

        return new DataDetailTopic(topic);
    }
    public Page<DataDetailTopic> listarActivated(Pageable pageable) {
            return topicRepository.findByActivatedTrue(pageable).map(DataDetailTopic::new);
    }
    @Transactional
    public void cancel(DeleteTopicData deleteTopicData) {
        // Utiliza el repositorio para buscar un curso por su ID
        Optional<Topic> optionalTopic = topicRepository.findById(deleteTopicData.id());

        if(optionalTopic.isEmpty()){
            throw new IntegrityValidity("Topic with ID " + deleteTopicData.id() + " not found");
        }
        Topic topic = topicRepository.getReferenceById(deleteTopicData.id());
        topic.desactivateTopic();

    }

    @Transactional
    public void markAsSolved(UpdateSolvedData updateSolvedData) {

        Optional<Topic> optionalTopic = topicRepository.findById(updateSolvedData.id());


        if(optionalTopic.isEmpty()){
            throw new IntegrityValidity("Topic with ID " + updateSolvedData.id() + " not found");
        }

        Topic topicAnswer = optionalTopic.get();
        Long topicID = topicAnswer.getId();


        Optional<Answer> optionalAnswer = answerRepository.findByTopicId(topicID);

        if (optionalAnswer.isEmpty()) {
            throw new IntegrityValidity("Answer for Topic with ID " + topicID + " not found");
        }
        Answer answer = optionalAnswer.get();
        Long answerID = answer.getId();

        answer.markAsSolved();

        Topic topic = topicRepository.getReferenceById(updateSolvedData.id());
        topic.markAsSolved();
    }
}




