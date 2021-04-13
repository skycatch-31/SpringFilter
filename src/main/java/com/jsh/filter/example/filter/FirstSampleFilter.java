package com.jsh.filter.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/sample/filter/first")
public class FirstSampleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("FirstSampleFilter doFilterInternal() before");
        if (request.getRequestURI().startsWith("/sample/filter/first")) {
            logger.info("uri  startsWith [/sample/filter]");
            response.setStatus(401);
            return;
        }
        filterChain.doFilter(request, response);
        logger.info("FirstSampleFilter doFilterInternal() after");
    }

    @Override
    public void destroy() {
        logger.info("FirstSampleFilter destroy()");
        super.destroy();
    }
}
