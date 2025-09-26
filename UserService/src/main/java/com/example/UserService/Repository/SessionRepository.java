package com.example.UserService.Repository;

import com.example.UserService.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//This is to store the session entity
public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByTokenAndUser_Id(String token,long user_id);
}
