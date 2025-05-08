package com.task_management_app.Supervisor;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @PostMapping("/create")
    public ResponseEntity<?> createSupervisor(@RequestBody Supervisor supervisor) {
        return supervisorService.createSupervisor(supervisor);
    }

    @PutMapping("/update{id}")
    public ResponseEntity<?> updateSupervisor(@PathVariable String id, @RequestBody Supervisor supervisor) {
        return supervisorService.updateSupervisor(id, supervisor);
    }

    @GetMapping("view/all")
    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return supervisorService.getAllSupervisors();
    }

    @GetMapping("view/{id}")
    public ResponseEntity<?> getSupervisorById(@PathVariable String id) {
        return supervisorService.getSupervisorById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteSupervisor(@PathVariable String id) {
        return supervisorService.deleteSupervisor(id);
    }

    @GetMapping("/report")
    public ResponseEntity<?> supervisorReport(HttpServletResponse response) throws DocumentException, IOException {
        return supervisorService.allSupervisorReport(response);
    }

    //    @GetMapping("/hello")
//    public ResponseEntity<?> hello(){
//        return ResponseEntity.ok("hello");
//    }


}
