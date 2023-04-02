package com.trellolike.service.Interface;

import com.trellolike.model.Card;
import com.trellolike.model.List;

public interface CardService {

    Card[] getAll();

    Card get(String id);

    Card add(Card card);

    Card update(String id, Card card);

    void remove(String id);
}
