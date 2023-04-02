package com.trellolike.service.Interface;

import com.trellolike.model.List;

public interface ListService {

    List[] getAll();

    List get(String id);

    List add(List list);

    List update(String id, List list);

    void remove(String id);
}
