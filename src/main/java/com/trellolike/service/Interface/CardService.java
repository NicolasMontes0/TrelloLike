package com.trellolike.service.Interface;

import com.trellolike.model.Card;

import java.util.List;

public interface CardService {

    List<Card> getAll();

    Card get(Integer id);

    Card add(Card card);

    Card update(Integer id, Card card);

    void remove(Integer id);
}
