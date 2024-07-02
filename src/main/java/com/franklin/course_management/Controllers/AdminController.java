package com.franklin.course_management.Controllers;

import java.util.ArrayList;
import java.util.Date;
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
import com.franklin.course_management.GradeLevelEnum.GradeLevel;
import com.franklin.course_management.Repositories.*;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class AdminController implements ErrorController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	TeacherRepository teacherRepo;
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	StudentCoursesRepository studentCoursesRepo;

    @GetMapping("/admincourses")
    public String courses(Model model,
    		 HttpServletRequest request) {
    	
    	User u = (User) request.getSession().getAttribute("user");
    	    	
    	List<Course> c = courseRepo.findAll();
    	
    	Map<Long, User> teacherMap = new HashMap<>();
    	
    	
    	for (Course course : c) {
    		Teacher t = teacherRepo.findById(course.getTeacherId());
            User teacher = userRepo.findById(t.getUserId());
            if (teacher != null) {
                teacherMap.put(course.getTeacherId(), teacher);
            }
        }
    	
    	String test = u.getRole().toString();
    	
    	
    	model.addAttribute("role", u.getRole().toString());
        model.addAttribute("courses", c);
        model.addAttribute("teacherMap", teacherMap);
    	
        return "courses";
    }
    

	@GetMapping("/createCourse")
    public String createCourse(Model model,
    		 HttpServletRequest request) {
		
		User u = (User) request.getSession().getAttribute("user");
		
		if (!u.getRole().equals(Role.ADMIN)) {
			return "redirect:/welcome";
		}
		
    	//get all teachers
    	List<Teacher> tList = teacherRepo.findAll();
    	
    	//only show teachers that aren't at max credits
    	
    	List<User> availableTeachers = new ArrayList<>();
    	
    	for (Teacher t : tList) {
            if (t.getCredits() < 12) {
                User user = userRepo.findById(t.getUserId());
                if (user != null) {
                    availableTeachers.add(user);
                }
            }
        }
    	
    	model.addAttribute("teachers", availableTeachers);
    	
    	
        return "createCourse";
    }
    
    @PostMapping("/createCourse")
    public String createCourse(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "courseName") String courseName,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "credits") String credits,
            @RequestParam(value = "seats") String seats,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "teacher") long teacher,
            @RequestParam(value = "grade", required = false) String grade) {
    	
    	User u = (User) request.getSession().getAttribute("user");
		
		if (!u.getRole().equals(Role.ADMIN)) {
			return "redirect:/welcome";
		}
		
    	Course c = courseRepo.findByName(courseName);
    	Teacher t = teacherRepo.findByUserId(teacher);
    	GradeLevel gl = null;
    	
    	switch (grade) {
		case "Freshman" :
			gl = GradeLevel.FRESHMAN;
			break;
		case "Sophomore" :
			gl = GradeLevel.SOPHOMORE;
			break;
		case "Junior" :
			gl = GradeLevel.JUNIOR;
			break;
		case "Senior" :
			gl = GradeLevel.SENIOR;
			break;
		}
    	
    	if (c != null) {
    		model.addAttribute("error", "Duplicate course name listed, please choose another");
    		return "createCourse";
    	}

        Date javaDate = java.sql.Date.valueOf(startDate);
        Date javaDate2 = java.sql.Date.valueOf(endDate);
        
    	Course newCourse = new Course(t.getId(), description, courseName, Integer.parseInt(seats), Integer.parseInt(credits), gl, javaDate, javaDate2);
    	
    	courseRepo.save(newCourse);
    	
    	//update teacher credits
    	
    	int tCredits = Integer.parseInt(credits) + t.getCredits();
    	
    	t.setCredits(tCredits);
    	
    	teacherRepo.save(t);
    	
    	model.addAttribute("role", u.getRole().toString());
    	
    	return "redirect:/admincourses";
    	
    }
    
    @PostMapping("/deletecourse")
    public String deleteCourse(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "courseId") Long courseId) {
    	
    	User u = (User) request.getSession().getAttribute("user");
		
		if (!u.getRole().equals(Role.ADMIN)) {
			return "redirect:/welcome";
		}
    	
		Course c = courseRepo.findById(courseId.longValue());
		
		List<StudentCourses> sC = studentCoursesRepo.findByCourseId(c.getId());
		
		for (StudentCourses sc : sC) {
			if (sc.getCourseId() == c.getId()) {
				studentCoursesRepo.deleteById(sc.getId());
				System.out.println("Deleted from student_courses " + c.getName());
	    	}	
    }
		
		courseRepo.deleteById(c.getId());
    	
    	
    	return "redirect:/admincourses";
    }
    
    @GetMapping("/editcourse")
    public String editCourse(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "courseId") Long courseId) {
    	
    	User u = (User) request.getSession().getAttribute("user");
		
		if (!u.getRole().equals(Role.ADMIN)) {
			return "redirect:/welcome";
		}
		
		Course c = courseRepo.findById(courseId.longValue());
		model.addAttribute("course", c);
		
		//get all teachers
    	List<Teacher> tList = teacherRepo.findAll();
    	
    	//only show teachers that aren't at max credits
    	
    	List<User> availableTeachers = new ArrayList<>();
    	
    	for (Teacher t : tList) {
            if (t.getCredits() < 12) {
                User user = userRepo.findById(t.getUserId());
                if (user != null) {
                    availableTeachers.add(user);
                }
            }
        }
    	
    	model.addAttribute("teachers", availableTeachers);
    	
    	return "editCourse";
    }
    
    @PostMapping("/editcourse")
    public String editCourse(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "courseName") String courseName,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "credits") String credits,
            @RequestParam(value = "seats") String seats,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "teacher") long teacher,
            @RequestParam(value = "courseId") Long courseId) {
    	
    	User u = (User) request.getSession().getAttribute("user");
		
		if (!u.getRole().equals(Role.ADMIN)) {
			return "redirect:/welcome";
		}
		
		Course c = courseRepo.findById(courseId.longValue());
	    if (c == null) {
	    	redirectAttributes.addAttribute("courseId", c.getId());
			redirectAttributes.addFlashAttribute("error", "Course not found");
			return "redirect:/editcourse";
	    }
		
		Teacher t = teacherRepo.findById(c.getTeacherId());
		
		if (t.getCredits() - c.getCredits() + Integer.parseInt(credits) > 12) {
			redirectAttributes.addAttribute("courseId", c.getId());
			redirectAttributes.addFlashAttribute("error", "Teacher can't have more than 12 credits");
			return "redirect:/editcourse";
		}
		
		Date javaDate = java.sql.Date.valueOf(startDate);
        Date javaDate2 = java.sql.Date.valueOf(endDate);
		
		if (javaDate.after(javaDate2)) {
			redirectAttributes.addAttribute("courseId", c.getId());
			redirectAttributes.addFlashAttribute("error", "Start date can't be after end date.");
			return "redirect:/editcourse";
		}
		
		Course c1 = courseRepo.findByName(courseName.trim());
		
		if (c1 != null && courseId.longValue() != c.getId()) {
			redirectAttributes.addAttribute("courseId", c.getId());
			redirectAttributes.addFlashAttribute("error", "Can't have duplicate names.");
			return "redirect:/editcourse";
		}
		
		int tCredits = t.getCredits() - c.getCredits() + Integer.parseInt(credits);
		
		t.setCredits(tCredits);
		teacherRepo.save(t);
		
		c.setName(courseName);
	    c.setDescription(description);
	    c.setCredits(Integer.parseInt(credits));
	    c.setSeats(Integer.parseInt(seats));
	    c.setStartDate(javaDate);
	    c.setEnd_date(javaDate2);
	    c.setTeacherId(t.getId());

	    courseRepo.save(c);
	   
	    
	    
	    
		return "redirect:/admincourses";
    }
    
}