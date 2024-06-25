package com.franklin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.franklin.course_management.Entities.Teacher;
import com.franklin.course_management.Entities.User;
import com.franklin.course_management.Entities.User.Role;
import com.franklin.course_management.Repositories.TeacherRepository;
import com.franklin.course_management.Repositories.UserRepository;

@SpringBootApplication
public class CourseManagement {

	@Autowired
    private UserRepository userRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CourseManagement.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		
//			User t1 = new User("test1234@gmail.com", "testTeacher2", "teacher123456", "Y", "Steph", "Curry", Role.TEACHER);
//			userRepo.save(t1);
//			
//			Teacher t = new Teacher(t1.getId(), 0);
//			teacherRepo.save(t);
		
		
		return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
	}
}
