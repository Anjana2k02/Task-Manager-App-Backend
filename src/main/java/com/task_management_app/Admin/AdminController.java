package com.task_management_app.Admin;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/update{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable String id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    @GetMapping("view/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("view/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable String id) {
        return adminService.getAdminById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String id) {
        return adminService.deleteAdmin(id);
    }

    @GetMapping("/report")
    public ResponseEntity<?> adminReport(HttpServletResponse response) throws DocumentException, IOException {
        return adminService.allAdminReport(response);
    }
}
