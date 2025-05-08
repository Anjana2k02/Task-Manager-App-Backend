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

    @Query(value = "{ 'progress' : 4 }", count = true)
    long countCompletedTasks();

    @Query(value = "{ 'progress' : 0 }", count = true)
    long countPendingTasks();

    @Query(value = "{ 'progress' : 1 }", count = true)
    long countDevelopingTasks();

    @Query(value = "{ 'progress' : 2 }", count = true)
    long countTestingTasks();

    @Query(value = "{ 'progress' : 2 }", count = true)
    long countQACompleteTasks();




}
