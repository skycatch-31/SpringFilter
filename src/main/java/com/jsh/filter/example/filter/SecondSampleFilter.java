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
//@WebFilter(urlPatterns = "/sample/filter/second")
public class SecondSampleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("SecondSampleFilter doFilterInternal() before");

        filterChain.doFilter(request, response);

        logger.info("SecondSampleFilter doFilterInternal() after");
    }

    @Override
    public void destroy() {
        logger.info("SecondSampleFilter destroy()");
        super.destroy();
    }
}
