package com.jpa.demo;

import com.jpa.demo.Service.StudentService;
import com.jpa.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudentTest {

    @Autowired
    private StudentService service;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void testCreateStudent() {
        // Create a student
        service.createStudent("John Doe", "john@example.com", 25);

        // Verify the student was persisted
        Student savedStudent = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", "john@example.com")
                .getSingleResult();

        assert savedStudent != null;
        assert "John Doe".equals(savedStudent.getFullName());
        assert 25 == savedStudent.getAge();
    }

    @Test
    @Transactional
    void testDeleteStudent() {
        // Create a student to delete
        service.createStudent("Jane Doe", "jane@example.com", 30);
        Student student = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", "jane@example.com")
                .getSingleResult();
        Long id = student.getId();

        // Delete the student
        service.deleteStudent(id);

        // Verify the student was deleted
        Long count = entityManager.createQuery("SELECT COUNT(s) FROM Student s WHERE s.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        assert count == 0;
    }
}
