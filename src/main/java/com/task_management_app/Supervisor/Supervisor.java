package com.task_management_app.Supervisor;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

@Data
@Document(collection = "supervisors")
public class Supervisor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String status;

    // Constructor to auto-generate ID if not set
    public Supervisor() {
        this.id = new ObjectId().toString(); // Generate new ObjectId if not provided
    }
}
