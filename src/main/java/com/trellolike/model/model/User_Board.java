package com.trellolike.model.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_BOARD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User_Board {
    @Id
    @GeneratedValue
    private Integer id_user_board;

    @Column(nullable = false)
    private Integer id_user;

    @Column(nullable = false)
    private Integer id_board;
}