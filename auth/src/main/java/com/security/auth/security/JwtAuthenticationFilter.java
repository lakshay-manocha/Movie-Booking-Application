package com.security.auth.security;


import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.auth.service.CustomUserDetails;
import com.security.auth.service.JWTService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomUserDetails userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {
    	
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
            }
            
            catch(JwtException e) {
                System.out.println("Invalid JWT Token");
            }

        }
        /*
         * Authenticate user only if
         * SecurityContext is empty
         */
        if(username != null &&
                SecurityContextHolder.getContext()
                .getAuthentication() == null) {
            UserDetails userDetails =
                    userDetailsService
                    .loadUserByUsername(username);

            if(jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
               
                authenticationToken
                .setDetails(
                        new WebAuthenticationDetailsSource()
                        .buildDetails(request)
                );
                SecurityContextHolder
                .getContext()
                .setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}