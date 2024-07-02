package com.franklin.course_management.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.franklin.course_management.Entities.*;
import com.franklin.course_management.Entities.User.Role;
import com.franklin.course_management.Repositories.*;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class StudentController implements ErrorController {
	
	@Autowired
	StudentCoursesRepository studentCoursesRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	TeacherRepository teacherRepo;
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	UserRepository userRepo;
	
	
	@GetMapping("/studentcourses")
	public String courses(Model model,
			HttpServletRequest request) {
		
		User u = (User) request.getSession().getAttribute("user");
		
		if (!u.getRole().equals(Role.STUDENT)) {
			return "redirect:/welcome";
		}
		
		Student s = studentRepo.findByUserId(u.getId());
		
		List<Course> courses = courseRepo.findAll();
		
		List<StudentCourses> c = studentCoursesRepo.findByStudentId(s.getId());
		
		List<Course> enrolledCourses = new ArrayList<>();
		List<Course> availableCourses = new ArrayList<>(courses);
		
		for (StudentCourses enrolled : c) {
			Course course = courseRepo.findById(enrolled.getCourseId());
			enrolledCourses.add(courseRepo.findById(course.getId()));
			
			availableCourses.remove(course);
		}
		
		Map<Long, User> teacherMap = new HashMap<>();
		
		for (Course course : courses) {
    		Teacher t = teacherRepo.findById(course.getTeacherId());
            User teacher = userRepo.findById(t.getUserId());
            if (teacher != null) {
                teacherMap.put(course.getTeacherId(), teacher);
            }
        }
		
		model.addAttribute("role", u.getRole().toString());
		model.addAttribute("courses", availableCourses);
		model.addAttribute("enrolledCourses", enrolledCourses);
		model.addAttribute("teacherMap", teacherMap);
    	
        return "courses";
    }
	
	@PostMapping("/enroll")
    public String enrollCourse(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "courseId") Long courseId) {
		
		User u = (User) request.getSession().getAttribute("user");
		
		Student student = studentRepo.findByUserId(u.getId());
		
		Course c = courseRepo.findById(courseId.longValue());
		
		StudentCourses s = new StudentCourses();
		
		if (c != null) {
			if (c.getCredits() + student.getCredits() <= 16
					&& c.getGrade().equals(student.getGrade()) && c.getSeats() > 1) {
	            s = new StudentCourses(student.getId(), courseId);
	            studentCoursesRepo.save(s);
	            int credits = student.getCredits();
	            credits += c.getCredits();
	            student.setCredits(credits);
	            studentRepo.save(student);
	            int seats = c.getSeats();
	            c.setSeats(seats - 1);
	            redirectAttributes.addFlashAttribute("toastMessage", "Successfully registered for " + c.getName());
	            return "redirect:/studentcourses";
			}
        } else {
            return "redirect:/studentcourses";
        }
		return "redirect:/studentcourses";
	}
	
	@PostMapping("/unenroll")
    public String unenrollCourse(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "courseId") Long courseId) {
		
		User u = (User) request.getSession().getAttribute("user");
		
		Student student = studentRepo.findByUserId(u.getId());
		
		Course c = courseRepo.findById(courseId.longValue());
		
		int credits = c.getCredits();
		
		int studentCredits = student.getCredits();
		
		int seats = c.getSeats();
		
		if (c != null) {
			List<StudentCourses> sC = studentCoursesRepo.findByStudentId(student.getId());
			for (StudentCourses c1 : sC) {
				if (c1.getStudentId() == student.getId()) {
					studentCoursesRepo.deleteById(c1.getId());
					c.setSeats(seats + 1);
					courseRepo.save(c);
					student.setCredits(studentCredits - credits);
					studentRepo.save(student);
					
				}
			}
		}
		
		return "redirect:/studentcourses";
	}
}