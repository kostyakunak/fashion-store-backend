package com.kounak.backend.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        String roles = null;

        // Проверяем заголовок Authorization
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwt);
                roles = jwtTokenUtil.getRolesFromToken(jwt);
                logger.info("[JWT] Токен получен. Username: {} | Roles: {}", username, roles);
            } catch (Exception e) {
                logger.error("[JWT] Ошибка разбора токена: {} | Токен: {}", e.getMessage(), jwt, e);
            }
        } else {
            logger.warn("[JWT] JWT Token does not begin with Bearer String. Authorization header: {}", authorizationHeader);
        }

        // Если имя пользователя из токена найдено и в контексте аутентификации еще нет пользователя
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Если токен валиден, устанавливаем аутентификацию в контекст
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                // Create authorities from roles in token
                Collection<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}