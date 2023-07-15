package com.trellolike.model.service.Interface;

import com.trellolike.model.model.Card_User;

import java.util.List;

public interface CardUserService {

    List<Card_User> getAll();

    Card_User get(Integer id);

    Card_User add(Card_User card_user);

    void remove(Integer id);
}
