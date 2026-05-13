package com.jpa.demo.Service;

import com.jpa.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        em.persist(s);
        System.out.println("Saved with ID: " + s.getId());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public void printAll(){
        em.createQuery("select s from Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }
    @Transactional
    public void updateStudent(Long id, String name, String email, int age) {
        Student s = em.find(Student.class, id);
        if (s != null) {
            s.setFullName(name);
            s.setEmail(email);
            s.setAge(age);
            em.merge(s);
            System.out.println("Updated student with ID: " + id);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student s = em.find(Student.class, id);
        if (s != null) {
            em.remove(s);
            System.out.println("Deleted student with ID: " + id);
        } else {
            throw new RuntimeException("Student not found");
        }
    }
}
