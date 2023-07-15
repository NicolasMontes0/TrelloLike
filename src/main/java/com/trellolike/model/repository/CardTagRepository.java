package com.trellolike.model.repository;

import com.trellolike.model.model.Card_Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTagRepository extends JpaRepository<Card_Tag, String> {
}
