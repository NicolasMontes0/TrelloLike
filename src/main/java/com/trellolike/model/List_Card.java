package com.trellolike.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIST_CARD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class List_Card {
    @Id
    @GeneratedValue
    private String id_list_card;

    @Column(nullable = false)
    private Integer id_list;

    @Column(nullable = false)
    private Integer id_card;
}
