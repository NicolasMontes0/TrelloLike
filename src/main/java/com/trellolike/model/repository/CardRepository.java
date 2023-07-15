package com.trellolike.model.repository;

import com.trellolike.model.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {

}
