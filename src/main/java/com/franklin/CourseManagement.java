package com.franklin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


import com.franklin.course_management.Entities.Student;
import com.franklin.course_management.Entities.User;
import com.franklin.course_management.Entities.User.Role;
import com.franklin.course_management.GradeLevelEnum.GradeLevel;
import com.franklin.course_management.Repositories.StudentRepository;
import com.franklin.course_management.Repositories.UserRepository;

@SpringBootApplication
public class CourseManagement {

	@Autowired
    private UserRepository userRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CourseManagement.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		
		if (userRepo.findByEmail("jamese08@email.franklin.edu") == null) {
			User admin1 = new User("jamese08@email.franklin.edu", "testAdmin", "admin123456", true, "Steph", "Curry", Role.ADMIN);
			userRepo.save(admin1);
		}
		
		if (userRepo.findByEmail("ejames@lec.edu") == null) {
			User student1 = new User("ejames@lec.edu", "testStudent", "student123456", true, "Klay", "Thompson", Role.STUDENT);
			userRepo.save(student1);
			
			Student s1 = new Student(student1.getId(), 0, GradeLevel.JUNIOR);
			studentRepo.save(s1);
		}
		
		
		return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
	}
}
