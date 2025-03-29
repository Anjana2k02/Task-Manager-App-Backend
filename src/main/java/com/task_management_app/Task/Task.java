package com.task_management_app.Task;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks") // Collection name changed to 'tasks'
public class Task {

    @Id
    private String id;            // Unique identifier for the task
    private String adminId;       // ID of the admin who created the task
    private String task;          // Task name or title
    private String description;   // Detailed description of the task
    private int days;             // Number of days to complete the task
    private String supervisorId;  // ID of the supervisor overseeing the task
    private String userId;        // ID of the user assigned to the task
    private int progress;         // Task progress percentage (0-100)
}
