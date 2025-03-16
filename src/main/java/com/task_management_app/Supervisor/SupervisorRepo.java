package com.task_management_app.Supervisor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupervisorRepo extends MongoRepository<Supervisor, String> {
    Optional<Supervisor> findByEmail(String email);
}
