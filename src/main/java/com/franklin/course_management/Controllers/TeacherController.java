package com.franklin.course_management.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
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
	AssignmentRepository assignmentRepo;

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
	StudentCoursesRepository studentCourseRepo;

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
    
    @GetMapping("/createAssignment")
    public String createAssignment(Model model,
    		 HttpServletRequest request,
    		 @RequestParam("courseId") Long courseId) {
    	
    	Course c = courseRepo.findById(courseId.longValue());
    	
    	model.addAttribute("course", c);
    	
    	return "createAssignment";
    }
    
    @PostMapping("/createAssignment")
	public String createAssignment(Model model,
		HttpServletRequest request,
		RedirectAttributes redirectAttributes,
		@RequestParam("courseId") Long courseId,
        @RequestParam("assignmentName") String name,
        @RequestParam("description") String description,
    	@RequestParam("endDate") String dueDate,
        @RequestParam("totalPoints") int totalPoints) {
		
    	Date due_date = java.sql.Date.valueOf(dueDate);
    	
		List<StudentCourses> students = studentCourseRepo.findByCourseId(courseId);

		for (StudentCourses student : students) {
			Assignment assignment = new Assignment(courseId.longValue(), student.getStudentId(), name, description, due_date, 0, totalPoints, "N");
			assignmentRepo.save(assignment);
        }

		List<Assignment> studentAssignment = assignmentRepo.findByCourseId(courseId.longValue());
		
		redirectAttributes.addAttribute("assignments", studentAssignment);
		
		redirectAttributes.addAttribute("courseId", courseId);

		return "redirect:/courseAssignments";
	}
    
    @GetMapping("/courseAssignments")
    public String courseAssignment(Model model,
    		 HttpServletRequest request,
    		 @RequestParam("courseId") Long courseId) {
    	
    	List<Assignment> studentAssignment = assignmentRepo.findByCourseId(courseId.longValue());
    	
    	Map<Long, User> studentMap = new HashMap<>();
    	
    	for (Assignment a : studentAssignment) {
    		Student s = studentRepo.findById(a.getStudentId());
            User student = userRepo.findById(s.getUserId());
            if (student != null) {
                studentMap.put(a.getStudentId(), student);
            }
        }
    	
    	model.addAttribute("assignments", studentAssignment);
    	model.addAttribute("studentMap", studentMap);
    	
    	return "courseAssignments";
    }
    
    @GetMapping("/viewStudents")
    public String viewStudents(Model model,
    		 HttpServletRequest request,
    		 @RequestParam("courseId") Long courseId) {
    	
    	List<StudentCourses> studentCourses = studentCourseRepo.findByCourseId(courseId.longValue());
    	
    	List<User> students = new ArrayList<>();
    	Map<Long, Student> studentMap = new HashMap<>();
    	
    	for (StudentCourses sc : studentCourses) {
    		Student s = studentRepo.findById(sc.getStudentId());
            User student = userRepo.findById(s.getUserId());
            if (student != null) {
                students.add(student);
                studentMap.put(student.getId(), s);
            }
        }
    	
    	model.addAttribute("students", students);
    	model.addAttribute("studentMap", studentMap);
    	
    	return "viewStudents";
    }

	@PostMapping("/grade")
	public String grade(@RequestParam Long assignmentId, @RequestParam int pointsEarned) {
		Assignment assignment = assignmentRepo.findById(assignmentId).orElse(null);
		if (assignment.getIsSubmitted().equals("Y")) {
			assignment.setPointsEarned(pointsEarned);
			//Potential value in Assignment Entity to tell if assignment is graded?
			assignmentRepo.save(assignment);
		}
		return "grade";
	}
}