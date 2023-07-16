package com.trellolike.model.repository;

import com.trellolike.model.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListRepository extends JpaRepository<List, String>  {

    @Query(value = "select l.* from List l where l.id_board = :id_board", nativeQuery = true)
    java.util.List<List> getByProject(@Param("id_board") Integer id);
}
