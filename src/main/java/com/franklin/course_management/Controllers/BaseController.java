package com.franklin.course_management.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController implements ErrorController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/page2")
    public String page2() {
        return "page2";
    }
}