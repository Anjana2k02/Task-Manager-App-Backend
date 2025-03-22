package com.task_management_app.Worker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepo extends MongoRepository<Worker, String> {
    Optional<Worker> findByEmail(String email);
}
