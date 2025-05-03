package com.task_management_app.Task;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "tasks") // Collection name changed to 'tasks'
public class Task {

    @Id
    private String id;            // lash
    private String t_no;
    private String adminId;       // lash
    private String task;          // lash
    private String description;   // lash
    private LocalDate dueDate;             // Number of days to complete the task
    private int priority;         // 1 - urgent 2 - Need  3 - Normal
    private String supervisorId;  // ID of the supervisor overseeing the task
    private String userId;        // ID of the user assigned to the task
    private int progress;         // Task progress percentage (0-100)
}
