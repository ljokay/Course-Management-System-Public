package com.franklin.course_management.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

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
	
	@Autowired
	AssignmentRepository assignmentRepo;

	
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
		
		List<Assignment> assignment = assignmentRepo.findByStudentId(s.getId());
		
		Map<Long, String> grades = new HashMap<>();
		
		double totalPoints = 0;
		
		double earnedPoints = 0;
		
		double percentage = 0;
		
		
		String grade = "No graded assignments";
		
		
		List<Course> enrolledCourses = new ArrayList<>();
		List<Course> availableCourses = new ArrayList<>(courses);
		
		for (StudentCourses enrolled : c) {
			Course course = courseRepo.findById(enrolled.getCourseId());
			enrolledCourses.add(courseRepo.findById(course.getId()));
			
			availableCourses.remove(course);
			
			if (assignment.size() > 0 && !assignment.isEmpty()) {
				for (Assignment a : assignment) {
					if (a.getCourseId() == enrolled.getCourseId()) {
						if (a.getPointsEarned() != null) {
							totalPoints += a.getTotalPoints();
							earnedPoints += a.getPointsEarned(); 
						}
					}
				}
				
				if (earnedPoints > 0) {
					percentage = (earnedPoints / totalPoints) * 100;
				
					if (percentage >= 90) {
						grade = "A";
					} else if (percentage >= 80 && percentage < 90) {
						grade = "B";
					} else if (percentage >= 70 && percentage < 80) {
						grade = "C";
					} else if (percentage >= 60 && percentage < 70) {
						grade = "D";
					} else if (percentage < 60) {
						grade = "F";
					} else {
						grade = "N/A";
					}
				}
			}
			
			grades.put(enrolled.getCourseId(), grade);
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
		model.addAttribute("grades", grades);
    	
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
		
		Date d = new Date();
		
		//Error handling for incorrect grade
		if (student.getGrade() != c.getGrade()) {
			model.addAttribute("error", "Student grade does not match course grade");
    		return "enroll";
		}
		//Error handling for too many credits
		if (student.getCredits() > 16) {
			model.addAttribute("error", "Enrolled credits exceeds maxiumum allowed credits (16)");
		}
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
	            courseRepo.save(c);
	            List<Assignment> a = assignmentRepo.findByCourseId(courseId);
	            for (Assignment a1 : a) {
	            	if (a1.getDueDate().before(d)) {
		            	Assignment create = new Assignment(courseId.longValue(), student.getId(), a1.getName(), a1.getDescription(), 
		            			a1.getDueDate(), null, a1.getTotalPoints(), "N");
		            	assignmentRepo.save(create);
	            	}
	            }
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
				if (c1.getStudentId() == student.getId() && c1.getCourseId() == courseId) {
					studentCoursesRepo.deleteById(c1.getId());
					c.setSeats(seats + 1);
					courseRepo.save(c);
					student.setCredits(studentCredits - credits);
					studentRepo.save(student);
					
					List<Assignment> assignment = assignmentRepo.findByStudentId(c1.getStudentId());
					
					if (!assignment.isEmpty() && assignment.size() > 0) {
						for (Assignment a : assignment) {
							if (a.getCourseId() == courseId) {
								assignmentRepo.delete(a);
							}
						}
					}
					
				}
			}
		}
		
		return "redirect:/studentcourses";
	}
	
	@PostMapping("/assignments")
    public String assignment(Model model,
    		 HttpServletRequest request,
    		 @RequestParam("courseId") Long courseId) {
    	
		User u = (User) request.getSession().getAttribute("user");
		
		Student student = studentRepo.findByUserId(u.getId());
		
    	List<Assignment> assignment = assignmentRepo.findByCourseId(courseId);
    	
    	List<Assignment> studentAssignment = new ArrayList<>();
    	
    	for (Assignment a : assignment) {
    		if (a.getStudentId() == student.getId()) {
    			studentAssignment.add(a);
    		}
        }
    	
    	model.addAttribute("assignments", studentAssignment);
    	
    	return "assignments";
    }

	@PostMapping("/submit")
	public String submitAssignment(Model model, HttpServletRequest request, Long assignmentId) {
		
		User u = (User) request.getSession().getAttribute("user");
		
		Assignment assignment = assignmentRepo.findById(assignmentId).orElse(null);
		
		Student student = studentRepo.findByUserId(u.getId());
		
    	List<Assignment> assignment1 = assignmentRepo.findByCourseId(assignment.getCourseId());
    	
    	List<Assignment> studentAssignment = new ArrayList<>();
    	
    	for (Assignment a : assignment1) {
    		if (a.getStudentId() == student.getId()) {
    			studentAssignment.add(a);
    		}
        }
		
		if (!assignment.getIsSubmitted().equals("Y")) {
			assignment.setIsSubmitted("Y");
			assignmentRepo.save(assignment);
		} else {
			
			model.addAttribute("error", "Assignment already submitted.");
			model.addAttribute("assignments", studentAssignment);
			return "assignments";
		}
		
    	
    	model.addAttribute("assignments", studentAssignment);
    	
		return "assignments";
	}
}

