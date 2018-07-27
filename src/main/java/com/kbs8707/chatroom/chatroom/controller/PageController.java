package com.kbs8707.chatroom.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//Controller for all page navigation related requests
@Controller
public class PageController {

    @RequestMapping("/chatroom")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //Add username to session attribute
        session.setAttribute("username", request.getParameter("username"));
        return "chat";
    }

    //    @RequestMapping(value = "/chatroom", method = RequestMethod.POST)
//    public ModelAndView messages(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView("chat");
//        mav.addObject("username", request.getParameter("username"));
//        return mav;
//    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
