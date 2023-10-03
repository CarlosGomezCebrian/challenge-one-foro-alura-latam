package edu.foro.api.domain.course;

import edu.foro.api.infra.errors.IntegrityValidity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public DataDetailCourse setDataRegistrationCourse(DataRegistrationCourse dataRegistrationCourse) {
        Course course = new Course(dataRegistrationCourse);
        courseRepository.save(course);
        return new DataDetailCourse(course);
    }

    public Page<DataDetailCourse> listarActivated(Pageable pageable) {
        return courseRepository.findByActivatedTrue(pageable).map(DataDetailCourse::new);
    }

    @Transactional
    public void inactive(InactiveCourseData inactiveCourseData) {

        Optional<Course> optionalCourse = courseRepository.findById(inactiveCourseData.id());
        if(optionalCourse.isEmpty()){
            throw new IntegrityValidity("Course with ID " + inactiveCourseData.id() + " not found");
        }
        Course course = courseRepository.getReferenceById(inactiveCourseData.id());
        course.inactiveUser();
    }

    @Transactional
    public void active(ActiveCourseData activeCourseData) {

        Optional<Course> optionalCourse = courseRepository.findById(activeCourseData.id());
        if(optionalCourse.isEmpty()){
            throw new IntegrityValidity("Course with ID " + activeCourseData.id() + " not found");
        }
        Course course = courseRepository.getReferenceById(activeCourseData.id());
        course.activeUser();
    }
}

