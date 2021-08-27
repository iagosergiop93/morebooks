package com.morebooks.morebooks.controllers;

import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter
public class PreFilter extends HttpFilter {

    private static final long serialVersionUID = -9149957407241653239L;

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        attachCorsResponseHeaders(resp);
        chain.doFilter(req, resp);
        return;
    }


    /**
     * Attaches the required appropriate response headers to satisfy
     * the CORS pre-flight request requirements.
     *
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    public void attachCorsResponseHeaders(HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("Attaching CORS headers to HTTP response");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-type, Authorization");
        resp.setHeader("Access-Control-Expose-Headers", "Authorization");
    }

}
