package edu.foro.api.controller;

import edu.foro.api.domain.course.*;
import edu.foro.api.infra.errors.IntegrityValidity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(
            summary = "Register a new course",
            description = "",
            tags = { "Course", "Post" })
    public ResponseEntity<DataDetailCourse> setDataRegistrationCourse(@RequestBody @Valid DataRegistrationCourse dataRegistrationCourse)throws IntegrityValidity {
        var response = courseService.setDataRegistrationCourse(dataRegistrationCourse);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "List of available courses",
            description = "",
            tags = { "Course", "Get" })
    public ResponseEntity<Page<DataDetailCourse>> courseList(@PageableDefault(size = 15, sort = {"id"}) Pageable pageable){
        var response = courseService.listarActivated(pageable);
     return ResponseEntity.ok(response);
    }
    @DeleteMapping
    @Operation(
            summary = "Inactivate unavailable courses",
            description = "",
            tags = { "Course", "Delete" })
    public ResponseEntity inactiveCourse(@RequestBody @Valid InactiveCourseData inactiveCourseData){
        courseService.inactive(inactiveCourseData);
        return ResponseEntity.ok("Course with ID " + inactiveCourseData.id() + " successfully inactive");
    }

    @PutMapping
    @Operation(
            summary = "Activate available courses",
            description = "",
            tags = { "Course", "Put" })
    public ResponseEntity activeCourse(@RequestBody @Valid ActiveCourseData activeCourseData) throws IntegrityValidity{
        courseService.active(activeCourseData);
        return ResponseEntity.ok("Course with ID " + activeCourseData.id() + " successfully active");
    }

}
