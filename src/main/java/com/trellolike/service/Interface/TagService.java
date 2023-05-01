package com.trellolike.service.Interface;

import com.trellolike.model.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAll();

    Tag get(Integer id);

    Tag add(Tag tag);

    Tag update(Integer id, Tag tag);

    void remove(Integer id);
}
