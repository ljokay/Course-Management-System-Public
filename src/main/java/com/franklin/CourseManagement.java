package com.franklin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.franklin.course_management.Entities.Student;
import com.franklin.course_management.Entities.Teacher;
import com.franklin.course_management.Entities.User;
import com.franklin.course_management.Entities.User.Role;
import com.franklin.course_management.GradeLevelEnum.GradeLevel;
import com.franklin.course_management.Repositories.AdminRepository;
import com.franklin.course_management.Repositories.CourseRepository;
import com.franklin.course_management.Repositories.StudentRepository;
import com.franklin.course_management.Repositories.TeacherRepository;
import com.franklin.course_management.Repositories.UserRepository;

@SpringBootApplication
public class CourseManagement {

	@Autowired
    private UserRepository userRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CourseManagement.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		
//			User t1 = new User("testTeacher2@gmail.com", "testTeacher2", "teacher123456", "Y", "Bill", "Russell", Role.TEACHER);
//			userRepo.save(t1);
//			
//			Teacher t = new Teacher(t1.getId(), 0);
//			teacherRepo.save(t);
		
		long a = 1702;
		long b = 152;
		
		adminRepo.deleteById(b);
		userRepo.deleteById(a);
		
		return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
	}
}
