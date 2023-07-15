package com.trellolike.model.repository;

import com.trellolike.model.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String>  {
}
