package com.trellolike.repository;

import com.trellolike.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, String>  {
}
