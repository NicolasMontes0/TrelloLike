package com.trellolike.service.Interface;

import com.trellolike.model.Member;
import com.trellolike.model.Tag;

public interface TagService {

    Tag[] getAll();

    Tag get(String id);

    Tag add(Tag tag);

    Tag update(String id, Tag tag);

    void remove(String id);
}
