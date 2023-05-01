package com.trellolike.service.Interface;

import com.trellolike.model.List;

public interface ListService {

    java.util.List<List> getAll();

    List get(Integer id);

    List add(List list);

    List update(Integer id, List list);

    void remove(Integer id);
}
