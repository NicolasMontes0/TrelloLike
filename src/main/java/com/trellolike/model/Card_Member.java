package com.trellolike.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CARD_MEMBER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card_Member {
    @Id
    @GeneratedValue
    private String id_card_member;

    @Column(nullable = false)
    private Integer id_card;

    @Column(nullable = false)
    private Integer id_member;
}