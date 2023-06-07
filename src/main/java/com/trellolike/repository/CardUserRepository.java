package com.trellolike.repository;

import com.trellolike.model.Card_User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardUserRepository extends JpaRepository<Card_User, String> {

}
