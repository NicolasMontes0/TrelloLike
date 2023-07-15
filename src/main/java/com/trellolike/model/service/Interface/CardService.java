package com.trellolike.model.service.Interface;

import com.trellolike.model.model.Card;

import java.util.List;

public interface CardService {

    List<Card> getAll();

    Card get(Integer id);

    Card add(Card card);

    Card update(Integer id, Card card);

    void remove(Integer id);
}
