package com.task_management_app.Auth;

// DTO for login response
public class AuthResponse {
    private String token;

    // Constructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter and setter
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}