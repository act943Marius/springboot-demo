package de.haw.hamburg.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.haw.hamburg.demo.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
