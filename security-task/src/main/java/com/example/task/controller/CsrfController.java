package com.example.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;

@Controller
public class CsrfController {
    @RequestMapping("/form")
    public String getForm() {
        return "form";
    }
    @RequestMapping("/csrf")
    public HttpStatus getCsrfToken() {
        return HttpStatus.OK;
    }
}
