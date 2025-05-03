package com.task_management_app.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}