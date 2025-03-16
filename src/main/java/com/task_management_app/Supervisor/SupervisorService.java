package com.task_management_app.Supervisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupervisorService {

    Logger logger = LoggerFactory.getLogger(SupervisorService.class);

    @Autowired
    private SupervisorRepo supervisorRepo;

    public ResponseEntity<?> createSupervisor(Supervisor supervisor) {
        if (supervisorRepo.findByEmail(supervisor.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(supervisorRepo.save(supervisor));
    }

    public ResponseEntity<?> updateSupervisor(String id, Supervisor supervisor) {
        Optional<Supervisor> existingSupervisorOptional = supervisorRepo.findById(id);

        if (existingSupervisorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor not found");
        }

        Supervisor existingSupervisor = existingSupervisorOptional.get();
        existingSupervisor.setFirstName(supervisor.getFirstName());
        existingSupervisor.setLastName(supervisor.getLastName());
        existingSupervisor.setEmail(supervisor.getEmail());
        existingSupervisor.setPassword(supervisor.getPassword());
        existingSupervisor.setStatus(supervisor.getStatus());

        return ResponseEntity.ok(supervisorRepo.save(existingSupervisor));
    }

    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return ResponseEntity.ok(supervisorRepo.findAll());
    }

    public ResponseEntity<?> getSupervisorById(String id) {
        Optional<Supervisor> supervisor = supervisorRepo.findById(id);
        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor not found");
        }
        return ResponseEntity.ok(supervisor.get());
    }

    public ResponseEntity<?> deleteSupervisor(String id) {
        Optional<Supervisor> supervisor = supervisorRepo.findById(id);
        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor not found");
        }
        supervisorRepo.deleteById(id);
        return ResponseEntity.ok("Supervisor successfully deleted");
    }
}

