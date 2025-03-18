package com.task_management_app.Supervisor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "supervisors")
public class Supervisor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean status;
}