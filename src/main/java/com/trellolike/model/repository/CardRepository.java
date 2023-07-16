package com.trellolike.model.repository;

import com.trellolike.model.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {

    @Query(value = "select c.* from Card c where c.id_list = :id_list", nativeQuery = true)
    List<Card> findByIdList(@Param("id_list") Integer id);
}
