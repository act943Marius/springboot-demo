package de.haw.hamburg.demo.controller;

import de.haw.hamburg.demo.exception.ResourceNotFoundException;
import de.haw.hamburg.demo.model.Student;
import de.haw.hamburg.demo.repository.CourseRepository;
import de.haw.hamburg.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses/{courseId}/students")
    public List<Student> getStudentsByCourseId(@PathVariable Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }

    @GetMapping("/courses/{courseId}/students/{studentId}")
    public Student getCourse(@PathVariable Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (!student.isPresent())
            throw new ResourceNotFoundException("Student not found with id " + studentId);
        return student.get();
    }

    @PostMapping("/courses/{courseId}/students")
    public Student addStudent(@PathVariable Long courseId,
                            @Valid @RequestBody Student student) {
        return courseRepository.findById(courseId)
                .map(question -> {
                    student.setCourse(question);
                    return studentRepository.save(student);
                }).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
    }

    @PutMapping("/courses/{courseId}/students/{studentId}")
    public Student updateStudent(@PathVariable Long courseId,
                                 @PathVariable Long studentId,
                                 @Valid @RequestBody Student studentRequest) {
        if(!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setFirstname(studentRequest.getFirstname());
                    student.setLastname(studentRequest.getLastname());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long courseId,
                                           @PathVariable Long studentId) {
        if(!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        return studentRepository.findById(studentId)
                .map(answer -> {
                    studentRepository.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

    }
}
