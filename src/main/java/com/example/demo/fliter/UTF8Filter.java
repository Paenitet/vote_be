package com.example.demo.fliter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class UTF8Filter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("UTF8 Filter..");

        HttpServletRequest req = (HttpServletRequest) request;
        req.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }
}
