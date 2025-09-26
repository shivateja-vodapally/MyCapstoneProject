package com.example.UserService.Repository;

import com.example.UserService.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

//This is to store the session entity
public interface SessionRepository extends JpaRepository<Session,Long> {
}
