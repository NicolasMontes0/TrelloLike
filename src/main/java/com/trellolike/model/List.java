package com.trellolike.model;

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
    private String id_list;

    @Column(nullable = false)
    private Integer name;
}