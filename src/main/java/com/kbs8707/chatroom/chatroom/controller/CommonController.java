package com.kbs8707.chatroom.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
    @RequestMapping("/chatroom")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("username", request.getParameter("username"));
//        if (request.getParameter("username") == null || request.getParameter("username").isEmpty()) {
//            return "-1";
//        }
//        return "1";
        return "chat";
    }

    @RequestMapping("/userinfo")
    @ResponseBody
    public String userInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        return session.getAttribute("username").toString();
    }
}
