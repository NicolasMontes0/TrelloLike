package com.trellolike.export.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectList {

    private String name;

    private List<ProjectCard> cards;

    public ProjectList(String name, List<ProjectCard> projectCards) {
        this.name = name;
        this.cards = projectCards;
    }
}
