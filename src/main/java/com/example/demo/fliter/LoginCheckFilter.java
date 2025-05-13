package com.example.demo.fliter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        // 로그인 안 되어 있으면 리다이렉트
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        String uri = httpRequest.getRequestURI();

        // 세션 제외
        if (uri.contains("/login") || uri.contains("/register") || uri.contains("/main") || uri.contains("/session-test") || uri.contains("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        if (!loggedIn) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401
            httpResponse.getWriter().write("로그인이 필요합니다.");
            return;
        }
        
        chain.doFilter(request, response);
    }
}