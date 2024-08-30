package com.example.SBStd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller : Controller 선언
@RestController
public class MainController {

    /*
        Controller 의 경우, Method 생성 방법

        @RequestMapping("/url")
        public void methodName() {

        }
    */

    @RequestMapping("/sbStd")
    public String index() {
        System.out.println("Link Test"); // Spring Boot Console 출력
        return "axios&SpringBoot Test";
    }
}
