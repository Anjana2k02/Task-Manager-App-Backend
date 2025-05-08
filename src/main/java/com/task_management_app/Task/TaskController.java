package com.task_management_app.Task;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*") // Allows all origins
@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @GetMapping("/view/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/report")
    public ResponseEntity<?> taskReport(HttpServletResponse response) throws DocumentException, IOException {
        return taskService.allTaskReport(response);
    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<Task> updateProgress(
            @PathVariable String id,
            @RequestParam int progress
    ) {
        Task updatedTask = taskService.updateTaskProgress(id, progress);
        return ResponseEntity.ok(updatedTask);
    }

//    // 🆕 1. Tasks with no due date (not assigned)
//    @GetMapping("/task-not-assigned")
//    public ResponseEntity<List<Task>> getTasksWithNoDueDate() {
//        return ResponseEntity.ok(TaskRepo.findByDueDateIsNull());
//    }
//
//    // 🆕 2. Tasks with due date (assigned)
//    @GetMapping("/task-assigned")
//    public ResponseEntity<List<Task>> getTasksWithDueDate() {
//        return ResponseEntity.ok(TaskRepo.findByDueDateIsNotNull());
//    }

    @GetMapping("/task-count")
    public ResponseEntity<?> getTaskCount(){
        return taskService.getTaskCount();
    }

    @GetMapping("/completed-task-count")
    public ResponseEntity<?> getCompletedTaskCount(){
        return taskService.getCompletedTaskCount();
    }

    @GetMapping("/pending-task-count")
    public ResponseEntity<?> getPendingCount(){
        return taskService.getPendingCount();
    }

    @GetMapping("/testing-task-count")
    public ResponseEntity<?> getTestingCount(){
        return taskService.getTestingCount();
    }

    @GetMapping("/developing-task-count")
    public ResponseEntity<?> getDevelopingCount(){
        return taskService.getDevelopingCount();
    }

    @GetMapping("/qa-completed-task-count")
    public ResponseEntity<?> getQACompletedCount(){
        return taskService.getQACompletedCount();
    }

    @GetMapping("/in-completed-task-count")
    public ResponseEntity<?> getInCompletedCount(){
        return taskService.inCompletedTaskCount();
    }

}
