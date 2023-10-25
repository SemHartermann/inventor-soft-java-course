package com.example.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CsrfController {
    @RequestMapping("/form")
    public String getForm() {
        return "form";
    }

    @RequestMapping("/csrf")
    public String getCsrfToken() {
        return "csrf";
    }
}
