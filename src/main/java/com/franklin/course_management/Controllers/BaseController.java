package com.franklin.course_management.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.franklin.course_management.EmailSenderService;


import com.franklin.course_management.Entities.*;
import com.franklin.course_management.Entities.User.Role;
import com.franklin.course_management.GradeLevelEnum.GradeLevel;
import com.franklin.course_management.Repositories.*;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class BaseController implements ErrorController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	TeacherRepository teacherRepo;
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	StudentCoursesRepository studentCoursesRepo;
	
    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping(value = {"/", "", "/index"})
    public String index() {
    	
        return "index";
    }
    
    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }
    
    @GetMapping("/signout")
    public String signOut( HttpServletRequest request,
    		 RedirectAttributes redirectAttributes) {
    	
    	
    	request.getSession().setAttribute("user", null);
    	
    	
    	return "index";
    }
    
    @PostMapping("/signup")
    public String signUp(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "userName") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "role") String role,
            @RequestParam(value = "grade", required = false) String grade){
    	
		//Error Handling 
		//Username
		User userTest = userRepo.findByUsername(username.trim());
		if (userTest != null) {
			model.addAttribute("error", "This username is already in use.");
			return "signup";
		}
		//Email
		userTest = userRepo.findByEmail(email.trim());
		if (userTest != null) {
			model.addAttribute("error", "This email has already been registered");
			return "signup";
		}

    	Role r = null;
    	User user = null;
    	
    	if (role.equalsIgnoreCase("Student")) {
    		r = Role.STUDENT;
    		user = new User(email.trim(), username.trim(), password.trim(), "N", firstName.trim(), lastName.trim(), r);
    		userRepo.save(user);
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
    		Student s = new Student(user.getId(), 0, gl);
    		studentRepo.save(s);
    		
    	} else if (role.equalsIgnoreCase("Teacher")) {
    		r = Role.TEACHER;
    		user = new User(email.trim(), username.trim(), password.trim(), "N", firstName.trim(), lastName.trim(), r);
    		userRepo.save(user);
    		Teacher t = new Teacher(user.getId(), 0);
    		teacherRepo.save(t);
    	} else {
    		r = Role.ADMIN;
    		user = new User(email.trim(), username.trim(), password.trim(), "N", firstName.trim(), lastName.trim(), r);
    		userRepo.save(user);
    		Admin a = new Admin(user.getId());
    		adminRepo.save(a);
    	}
    	
    	String subject = "Click the link below to verify your account.";
    	String body = ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    	body = body + "/verify?code=" + user.getId();
    	
    	emailSenderService.sendEmail(user.getEmail(), subject, body);
    	
    	model.addAttribute("signup", "Check your email for verification");
    	
    	
        return "/index";
    }
    
    @GetMapping("/verify")
    public String verify(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "code") Long id){
    	
    	Optional<User> user = userRepo.findById(id);
    	
        if (user.isPresent()) {
        	
            User u = user.get();
            
            u.setVerified("Y");
            
            userRepo.save(u);
        }
    	
        return "index";
    }
    
    @PostMapping("/login")
    public String login(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password){
    	
    	User user = userRepo.findByUsername(username);
    	
    	if (user == null) {
    		model.addAttribute("error", "User doesn't exist, please sign up");
            return "index";
    	} else if (user.isVerified().equals("N")) {
    		model.addAttribute("error", "User not verified, please check email.");
            return "index";
    	}
    	if (password.equals(user.getPassword()) && username.equals(user.getUsername())) {
    		
    		request.getSession().setAttribute("user", user);
    		
    		model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
    		model.addAttribute("role", user.getRole().toString());
    		
    		if (user.getRole() == Role.STUDENT) {
    			Student s = studentRepo.findByUserId(user.getId());
    			model.addAttribute("credits", s.getCredits());
    		}
    		if (user.getRole() == Role.TEACHER) {
    			Teacher t = teacherRepo.findByUserId(user.getId());
    			model.addAttribute("credits", t.getCredits());
    		}

    		return "redirect:/welcome";
		} else {
    		model.addAttribute("error", "Incorrect Credentials, please try again.");
            return "index";
		}
    }
    
    @GetMapping("/welcome")
    public String welcome(Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
    	User user = (User) request.getSession().getAttribute("user");
    	
    	if (user == null) {
    		return "redirect:/index";
    	}
		
		if (user.getRole() == Role.STUDENT) {
			Student s = studentRepo.findByUserId(user.getId());
			List<StudentCourses> c = studentCoursesRepo.findByStudentId(s.getId());
			model.addAttribute("credits", s.getCredits());
			model.addAttribute("size", c.size());
		}
		if (user.getRole() == Role.TEACHER) {
			Teacher t = teacherRepo.findByUserId(user.getId());
			model.addAttribute("credits", t.getCredits());
		}
    	
    	model.addAttribute("role", user.getRole().toString());
    	model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
    	
    	return "welcome";
    }
}