package com.trellolike.model.repository;

import com.trellolike.model.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, String>  {
}
