//package com.kbs8707.chatroom.chatroom.config;
//
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Component
//public class SecurityConfig implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpSession session = req.getSession();
//
//        String requestPath = req.getServletPath();
//        if (requestPath.endsWith("login") || requestPath.endsWith("index")) {
//            chain.doFilter(request, response);
//        }
//        else {
//            if (session.getAttribute("username") == null && !requestPath.endsWith(".js") && !requestPath.endsWith(".css")) {
//                request.getRequestDispatcher("/index").forward(request, response);
//            } else {
//                chain.doFilter(request, response);
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {}
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//}
