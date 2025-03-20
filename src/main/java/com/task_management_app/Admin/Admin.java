package com.task_management_app.Admin;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "admins")
public class Admin {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean status;
}