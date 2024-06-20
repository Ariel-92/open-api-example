package com.rockercats.open_api.global;

import com.rockercats.open_api.model.User;
import com.rockercats.open_api.service.auth.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                if (jwtUtil.validateToken(authHeader)) {
                    String username = jwtUtil.getUsernameFromToken(authHeader);
                    User user = userService.getUserByUsername(username);
                    if (user != null) {
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}
