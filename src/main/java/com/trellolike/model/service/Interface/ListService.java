package com.trellolike.model.service.Interface;

import com.trellolike.model.model.ListModel;

public interface ListService {

    java.util.List<ListModel> getAll();

    ListModel get(Integer id);

    ListModel add(ListModel list);

    ListModel update(Integer id, ListModel list);

    void remove(Integer id);

    java.util.List<ListModel> getByProject(Integer id);
}
