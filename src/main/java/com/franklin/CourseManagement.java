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
import com.franklin.course_management.Repositories.StudentCoursesRepository;
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
	private StudentRepository studentRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private StudentCoursesRepository stuCourRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CourseManagement.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		
//		User u1 = new User("test1@gmail.com", "testStudent1", "test12345", "Y", "Test", "Student", Role.STUDENT);
//		userRepo.save(u1);
//		u1 = userRepo.findByUsername("testStudent1");
//		Student s1 = new Student(u1.getId(), 0, GradeLevel.SENIOR);
//		studentRepo.save(s1);
//		
//		User u2 = new User("test12@gmail.com", "testStudent2", "test12345", "Y", "Test", "Student", Role.TEACHER);
//		userRepo.save(u2);
//		User u2 = userRepo.findByEmail("test12@gmail.com");
//		u2.setUsername("testTeacher1");
//		userRepo.save(u2);
//		Teacher t = new Teacher(u2.getId(), 0);
//		teacherRepo.save(t);
		Long id = (long) 2202;
		adminRepo.deleteAll();
		userRepo.deleteById(id);
		
		
		
		return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
	}
}
