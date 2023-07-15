package com.trellolike.model.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CARD_TAG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card_Tag {
    @Id
    @GeneratedValue
    private Integer id_card_tag;

    @Column(nullable = false)
    private Integer id_card;

    @Column(nullable = false)
    private Integer id_tag;
}