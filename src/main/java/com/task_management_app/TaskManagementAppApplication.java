package com.task_management_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*") // Allows all origins (all ports)
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Task Management API",
				version = "1.0",
				description = "API Documentation for Task Management App"
		)
)
public class TaskManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementAppApplication.class, args);
	}

}
