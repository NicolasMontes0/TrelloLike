package com.trellolike.model.controller;

import com.trellolike.model.model.User_Board;
import com.trellolike.model.service.Interface.UserBoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/UsersBoards", "/usersBoards"})
@AllArgsConstructor
public class UserBoardController {

    private final UserBoardService userBoardService;

    @GetMapping
    public ResponseEntity<List<User_Board>> read() {
        return new ResponseEntity<>(userBoardService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User_Board> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(userBoardService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User_Board> create(@RequestBody User_Board user_board) {
        return new ResponseEntity<>(userBoardService.add(user_board), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userBoardService.remove(id);
        return "Operation OK";
    }
}