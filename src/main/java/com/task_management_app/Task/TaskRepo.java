package com.task_management_app.Task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends MongoRepository<Task, String> {
//    // You can add custom query methods here if needed
//    List<Task> findByDueDateIsNull();
//    // 1️⃣ Tasks with dueDate == null
//    List<Task> findByDueDateIsNull();

    // 2️⃣ Tasks with dueDate != null
//    List<Task> findByDueDateIsNotNull();

    @Query(value = "{}", count = true)
    long getTotalTaskCount();
}
