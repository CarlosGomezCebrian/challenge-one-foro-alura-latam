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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Page<DataDetailTopic> listActivated(Pageable pageable) {
            return topicRepository.findByActivatedTrue(pageable).map(DataDetailTopic::new);
    }

    public Page<DataDetailTopic> listNoResponse(Pageable pageable) {
        Page<Topic> topicsPage = topicRepository.findByActivatedTrue(pageable);

        // Filtrar los temas con Status igual a NO_RESPONSE
        List<DataDetailTopic> filteredTopics = topicsPage.getContent()
                .stream()
                .filter(topic -> topic.getStatus() == Status.NO_RESPONSE)
                .map(DataDetailTopic::new)
                .collect(Collectors.toList());

        // Crear una nueva Page con los temas filtrados
        Page<DataDetailTopic> filteredPage = new PageImpl<>(filteredTopics, pageable, filteredTopics.size());

        return filteredPage;
    }

    public Page<DataDetailTopic> listWhitResponse(Pageable pageable) {
        Page<Topic> topicsPage = topicRepository.findByActivatedTrue(pageable);


        List<DataDetailTopic> filteredTopics = topicsPage.getContent()
                .stream()
                .filter(topic -> topic.getStatus() == Status.WHIT_RESPONSE || topic.getStatus() == Status.RESOLVED)
                .map(DataDetailTopic::new)
                .collect(Collectors.toList());

        // Crear una nueva Page con los temas filtrados
        Page<DataDetailTopic> filteredPage = new PageImpl<>(filteredTopics, pageable, filteredTopics.size());

        return filteredPage;
    }

    public Page<DataDetailTopic> listResolved(Pageable pageable) {
        Page<Topic> topicsPage = topicRepository.findByActivatedTrue(pageable);


        List<DataDetailTopic> filteredTopics = topicsPage.getContent()
                .stream()
                .filter(topic -> topic.getStatus() == Status.RESOLVED)
                .map(DataDetailTopic::new)
                .collect(Collectors.toList());

        // Crear una nueva Page con los temas filtrados
        Page<DataDetailTopic> filteredPage = new PageImpl<>(filteredTopics, pageable, filteredTopics.size());

        return filteredPage;
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
    public void markAsResolved(UpdateResolvedData updateResolvedData) {

        Optional<Topic> optionalTopic = topicRepository.findById(updateResolvedData.id());


        if(optionalTopic.isEmpty()){
            throw new IntegrityValidity("Topic with ID " + updateResolvedData.id() + " not found");
        }

        Topic topic = optionalTopic.get();
        topic.markAsResolved();



        List<Answer> answers = answerRepository.findByTopicId(topic.getId());

        if (answers.isEmpty()) {
            throw new IntegrityValidity("Answer for Topic with ID " + topic.getId() + " not found");
        }
        for (Answer answer : answers) {
            answer.markAsResolved(); // Marca todas las respuestas relacionadas con el tema como resueltas
        }





    }


}


/*
@Transactional
public void markAsResolved(UpdateResolvedData updateResolvedData) {


    List<Answer> answers = answerRepository.findByTopicId(topic.getId());
    for (Answer answer : answers) {
        answer.markAsResolved(); // Marca todas las respuestas relacionadas con el tema como resueltas
    }
}

        Optional<Answer> optionalAnswer = answerRepository.findByTopicId(topic.getId());

        if (answers.isEmpty()) {
            throw new IntegrityValidity("Answer for Topic with ID " + topic.getId() + " not found");
        }

        Answer answer = optionalAnswer.get();


        answer.markAsResolved();

 */

