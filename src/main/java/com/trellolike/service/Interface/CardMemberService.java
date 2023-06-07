package com.trellolike.service.Interface;

import com.trellolike.model.Card_User;

import java.util.List;

public interface CardMemberService {

    List<Card_User> getAll();

    Card_User get(Integer id);

    Card_User add(Card_User card_user);

    void remove(Integer id);
}
