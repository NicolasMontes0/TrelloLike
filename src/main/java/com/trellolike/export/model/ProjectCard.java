package com.trellolike.export.model;

import lombok.Data;

@Data
public class ProjectCard {

    private String name;

    private String description;

    public ProjectCard(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
