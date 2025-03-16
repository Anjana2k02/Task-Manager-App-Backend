package com.task_management_app.Supervisor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/supervisors")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @PostMapping("/create")
    public ResponseEntity<?> createSupervisor(@RequestBody Supervisor supervisor) {
        return supervisorService.createSupervisor(supervisor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupervisor(@PathVariable String id, @RequestBody Supervisor supervisor) {
        return supervisorService.updateSupervisor(id, supervisor);
    }

    @GetMapping("")
    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return supervisorService.getAllSupervisors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupervisorById(@PathVariable String id) {
        return supervisorService.getSupervisorById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupervisor(@PathVariable String id) {
        return supervisorService.deleteSupervisor(id);
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }
}
