package com.task_management_app.Admin;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "admins")
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private final String type = "admin";
    private Boolean status;

}