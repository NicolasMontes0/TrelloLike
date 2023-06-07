package com.trellolike.service.Interface;

import com.trellolike.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(Integer id);

    User add(User user);

    User update(Integer id, User user);

    void remove(Integer id);
}
