package com.trellolike.model.repository;

import com.trellolike.model.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u.id_user FROM User u WHERE u.pseudo = :pseudo AND u.password = :password")
    String getUser(@Param("pseudo") String pseudo, @Param("password") String password);

    @Query(value = "SELECT u.* FROM User u WHERE u.pseudo = :pseudo", nativeQuery = true)
    User getUserByPseudo(@Param("pseudo") String pseudo);
}
