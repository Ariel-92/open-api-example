package com.rockercats.open_api.security.member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class UserAuthFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain;
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws IOException, ServletException {
        if(!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }
        try{
        }
        catch (InternalAuthenticationServiceException e) {

        }
    }
}
