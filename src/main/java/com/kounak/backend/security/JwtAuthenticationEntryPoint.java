package com.kounak.backend.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * This class is used to handle unauthorized requests
 * It rejects every unauthenticated request and sends error code 401
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
                             
        // Log the unauthorized access attempt
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String message = authException.getMessage();
        
        System.out.println("Unauthorized access attempt - Path: " + requestURI + 
                          ", IP: " + remoteAddr + 
                          ", Message: " + message);
        
        // Determine the error based on the HTTP method and path
        String errorMessage = "Unauthorized access";
        int status = HttpServletResponse.SC_UNAUTHORIZED;  // 401
        
        // If it's an admin resource access
        if (requestURI.contains("/api/admin")) {
            errorMessage = "Access denied. Administrative privileges required.";
            status = HttpServletResponse.SC_FORBIDDEN; // 403 - Indicates lack of permission
        } else {
            errorMessage = "Authentication required. Please login.";
        }
        
        // Prepare a more detailed error response
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", status);
        responseBody.put("message", errorMessage);
        responseBody.put("error", authException.getMessage());
        responseBody.put("path", requestURI);
        
        // Configure the response
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        // Write the error response as JSON
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}