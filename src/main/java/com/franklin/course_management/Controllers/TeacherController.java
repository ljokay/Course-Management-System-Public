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
public class TeacherController implements ErrorController {
	
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

    @GetMapping("/teachercourses")
    public String courses(Model model,
    		 HttpServletRequest request) {
    	
    	User u = (User) request.getSession().getAttribute("user");
    	    	
    	List<Course> c = courseRepo.findAll();
    	List<Course> teacherCourses = new ArrayList<>();
    	
    	Map<Long, User> teacherMap = new HashMap<>();
    	
    	
    	for (Course course : c) {
    		Teacher t = teacherRepo.findById(course.getTeacherId());
            User teacher = userRepo.findById(t.getUserId());
            if (teacher != null) {
                teacherMap.put(course.getTeacherId(), teacher);
            }
            if (t.getUserId() == u.getId()) {
            	teacherCourses.add(course);
            }
        }
    	
    	String test = u.getRole().toString();
    	
    	
    	model.addAttribute("role", u.getRole().toString());
        model.addAttribute("courses", teacherCourses);
        model.addAttribute("teacherMap", teacherMap);
    	
        return "courses";
    }
    
    
}