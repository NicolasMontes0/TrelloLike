package com.trellolike.model.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class List {
    @Id
    @GeneratedValue
    private Integer id_list;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer id_board;
}