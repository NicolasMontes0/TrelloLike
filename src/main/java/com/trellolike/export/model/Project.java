package com.trellolike.export.model;

import lombok.Data;

import java.util.List;

@Data
public class Project {

    private String name;

    private List<ProjectList> lists;

    public Project(String name, List<ProjectList> projectList) {
        this.name = name;
        this.lists = projectList;
    }
}
