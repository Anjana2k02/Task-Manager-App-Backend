package com.task_management_app.Auth;

import com.task_management_app.Admin.Admin;
import com.task_management_app.Admin.AdminRepo;
import com.task_management_app.Auth.dto.LoginRequest;
import com.task_management_app.Auth.dto.LoginResponse;
import com.task_management_app.Supervisor.Supervisor;
import com.task_management_app.Supervisor.SupervisorRepo;
import com.task_management_app.Worker.Worker;
import com.task_management_app.Worker.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private SupervisorRepo supervisorRepo;
    @Autowired
    private WorkerRepo workerRepo;

    // Constructor-based injection
    public AuthService(AdminRepo adminRepo,
                       SupervisorRepo supervisorRepo,
                       WorkerRepo workerRepo) {
        this.adminRepo = adminRepo;
        this.supervisorRepo = supervisorRepo;
        this.workerRepo = workerRepo;
    }

    public ResponseEntity<?> login(LoginRequest request) {
        // Check Admin
        Optional<Admin> admin = adminRepo.findByEmail(request.getEmail());
        if (admin.isPresent()) {
            if (admin.get().getPassword().equals(request.getPassword())) {
                LoginResponse response = new LoginResponse(admin.get().getId(), admin.get().getType());
                return ResponseEntity.ok(response);
            }
        }

        // Check Supervisor
        Optional<Supervisor> supervisor = supervisorRepo.findByEmail(request.getEmail());
        if (supervisor.isPresent()) {
            if (supervisor.get().getPassword().equals(request.getPassword())) {
                LoginResponse response = new LoginResponse(supervisor.get().getId(), supervisor.get().getType());
                return ResponseEntity.ok(response);
            }
        }

        // Check Worker
        Optional<Worker> worker = workerRepo.findByEmail(request.getEmail());
        if (worker.isPresent()) {
            if (worker.get().getPassword().equals(request.getPassword())) {
                LoginResponse response = new LoginResponse(worker.get().getId(), worker.get().getType());
                return ResponseEntity.ok(response);
            }
        }

        // Fallback - no match
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

}