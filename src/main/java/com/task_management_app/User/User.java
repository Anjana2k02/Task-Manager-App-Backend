package com.task_management_app.User;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private int devType;
    private String country;
    private String userType;

}
