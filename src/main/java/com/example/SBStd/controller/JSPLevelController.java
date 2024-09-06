package com.example.SBStd.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

// JSP 단계 설계 방법
@RequestMapping("/jsp/*")
@Controller
public class JSPLevelController {

    @GetMapping("/test1")
    @ResponseBody
    public void jspTransferData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        resp.getWriter().append("%d + %d = %d".formatted(a, b, a + b));
    }
}
