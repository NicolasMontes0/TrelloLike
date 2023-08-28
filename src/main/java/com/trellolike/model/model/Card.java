package com.trellolike.model.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CARD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue
    private Integer id_card;

    @Column(nullable = false, length = 500)
    private String name;

    @Column(length = 500)
    private String description;

    private String date;

    @Column(nullable = false)
    private Integer id_list;
}