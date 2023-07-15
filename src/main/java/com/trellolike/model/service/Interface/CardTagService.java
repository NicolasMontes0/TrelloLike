package com.trellolike.model.service.Interface;

import com.trellolike.model.model.Card_Tag;

import java.util.List;

public interface CardTagService {

    List<Card_Tag> getAll();

    Card_Tag get(Integer id);

    Card_Tag add(Card_Tag card_tag);

    void remove(Integer id);
}
