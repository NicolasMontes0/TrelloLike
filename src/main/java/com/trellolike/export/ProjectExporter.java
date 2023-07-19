package com.trellolike.export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.export.model.Project;
import com.trellolike.export.model.ProjectCard;
import com.trellolike.export.model.ProjectList;
import com.trellolike.model.model.Board;
import com.trellolike.model.model.Card;
import com.trellolike.model.model.ListModel;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import org.springframework.http.HttpMethod;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public class ProjectExporter {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public ProjectExporter() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    public void exportProjectJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String projectStr = apiCaller.callApi(null, "/boards/" + Current.projectId, HttpMethod.GET);
            Board project = objectMapper.readValue(projectStr, Board.class);
            String listsStr = apiCaller.callApi(null, "/lists/boards/" + Current.projectId, HttpMethod.GET);
            java.util.List<ListModel> lists = objectMapper.readValue(listsStr, new TypeReference<>() {});


            List<ProjectList> projectLists = new LinkedList<>();
            for (ListModel list : lists) {
                String cardsStr = apiCaller.callApi(null, "/cards/lists/" + list.getId_list(), HttpMethod.GET);
                java.util.List<Card> cards = objectMapper.readValue(cardsStr, new TypeReference<>() {});

                List<ProjectCard> projectCards = new LinkedList<>();
                for (Card card : cards) {
                    ProjectCard projectCard = new ProjectCard(card.getName(), card.getDescription());
                    projectCards.add(projectCard);
                }
                ProjectList projectList = new ProjectList(list.getName(), projectCards);
                projectLists.add(projectList);
            }
            Project projetComplet = new Project(project.getName(), projectLists);


            String projetJson = objectMapper.writeValueAsString(projetComplet);

            FileWriter fileWriter = new FileWriter("src/main/resources/exportFile/project.json");
            fileWriter.write(projetJson);
            fileWriter.close();

        } catch (Exception e) {
            loader.InternalError(e);
        }
    }
}
