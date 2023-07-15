package com.trellolike.model.repository;

import com.trellolike.model.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
