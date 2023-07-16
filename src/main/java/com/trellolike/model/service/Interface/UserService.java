package com.trellolike.model.service.Interface;

import com.trellolike.model.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(Integer id);

    String add(User user);

    User update(Integer id, User user);

    void remove(Integer id);

    String getUserByPseudoAndPassword(String pseudo, String password);

    User getUserByPseudo(String pseudo);
}
