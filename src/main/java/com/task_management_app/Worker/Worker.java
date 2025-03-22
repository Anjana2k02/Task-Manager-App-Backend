package com.task_management_app.Worker;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "workers")
public class Worker {

    @Id
    private String id; // MongoDB will generate this automatically

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
    private String status;
    private String expressionStatus;
}
