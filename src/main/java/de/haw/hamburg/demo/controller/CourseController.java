package de.haw.hamburg.demo.controller;

import de.haw.hamburg.demo.model.Course;
import de.haw.hamburg.demo.repository.CourseRepository;
import de.haw.hamburg.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public Page<Course> getCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }


    @PostMapping("/courses")
    public Course createCourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/courses/{courseId}")
    public Course updateCourse(@PathVariable Long courseId,
                                   @Valid @RequestBody Course courseRequest) {
        return courseRepository.findById(courseId)
                .map(question -> {
                    question.setTitle(courseRequest.getTitle());
                    question.setDescription(courseRequest.getDescription());
                    return courseRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
    }


    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourses(@PathVariable Long courseId) {
        return courseRepository.findById(courseId)
                .map(question -> {
                    courseRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
    }
}
