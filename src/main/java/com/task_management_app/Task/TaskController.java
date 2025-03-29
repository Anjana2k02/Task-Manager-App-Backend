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

    // Uncomment if needed
    // @GetMapping("/hello")
    // public ResponseEntity<?> hello() {
    //     return ResponseEntity.ok("hello");
    // }
}
