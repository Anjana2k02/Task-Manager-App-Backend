package com.task_management_app.Task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends MongoRepository<Task, String> {
    // You can add custom query methods here if needed
}
