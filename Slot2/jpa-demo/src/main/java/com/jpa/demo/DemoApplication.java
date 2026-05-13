package com.jpa.demo;

import com.jpa.demo.Service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
    @Bean
    public CommandLineRunner demo(StudentService service) {
        return args -> {
            service.createStudent("Alice Smith", "a@gmail.com", 20);
            service.createStudent("Bob Johnson", "b@gmail.com", 22);
            service.createStudent("Charlie Brown", "c@gmail.com", 19);
            service.updateStudent(1L, "Alice Smith", "smith@gmail.com", 21);
            service.deleteStudent(2L);
            service.printAll();
        };
    }
}
