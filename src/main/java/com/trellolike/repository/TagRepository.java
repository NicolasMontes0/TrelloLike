package com.trellolike.repository;

import com.trellolike.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String>  {
}
