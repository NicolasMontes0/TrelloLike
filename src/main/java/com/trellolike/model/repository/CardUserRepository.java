package com.trellolike.model.repository;

import com.trellolike.model.model.Card_User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardUserRepository extends JpaRepository<Card_User, String> {

}
