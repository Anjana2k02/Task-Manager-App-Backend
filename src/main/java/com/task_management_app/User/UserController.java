package com.task_management_app.User;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*") // Allows all origins (all ports)
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("view/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("view/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }


    @GetMapping("/report")
    public ResponseEntity<?> userReport(HttpServletResponse response) throws DocumentException, IOException {
        return userService.allUserReport(response);
    }

    @GetMapping("/view/devType/{devType}")
    public ResponseEntity<List<User>> getUsersByDevType(@PathVariable int devType) {
        return ResponseEntity.ok(userService.getUsersByDevType(devType));
    }


//    @GetMapping("/hello")
//    public ResponseEntity<?> hello(){
//        return ResponseEntity.ok("hello");
//    }

}
