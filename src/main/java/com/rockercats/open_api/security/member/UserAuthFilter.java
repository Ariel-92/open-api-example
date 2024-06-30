package com.rockercats.open_api.security.member;

import com.rockercats.open_api.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthFilter extends OncePerRequestFilter {
    private final UserAuthExtractor userAuthExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws IOException, ServletException {
        try{
            userAuthExtractor.extract(request)
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        }
        catch (InternalAuthenticationServiceException e) {
            logger.info(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
