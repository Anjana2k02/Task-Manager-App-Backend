package com.task_management_app.Worker;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/create")
    public ResponseEntity<?> createWorker(@RequestBody Worker worker) {
        return workerService.createWorker(worker);
    }

    @PutMapping("/update{id}")
    public ResponseEntity<?> updateWorker(@PathVariable String id, @RequestBody Worker worker) {
        return workerService.updateWorker(id, worker);
    }

    @GetMapping("view/all")
    public ResponseEntity<List<Worker>> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    @GetMapping("view/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable String id) {
        return workerService.getWorkerById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable String id) {
        return workerService.deleteWorker(id);
    }

    @GetMapping("/report")
    public ResponseEntity<?> workerReport(HttpServletResponse response) throws DocumentException, IOException {
        return workerService.allWorkerReport(response);
    }

    // Uncomment this if you want a test endpoint
    // @GetMapping("/hello")
    // public ResponseEntity<?> hello(){
    //     return ResponseEntity.ok("hello");
    // }
}
