package com.trellolike.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CARD_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card_User {
    @Id
    @GeneratedValue
    private Integer id_card_user;

    @Column(nullable = false)
    private Integer id_card;

    @Column(nullable = false)
    private Integer id_user;
}