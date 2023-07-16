package com.trellolike.model.controller;

import com.trellolike.model.model.User;
import com.trellolike.model.service.Interface.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/Users", "/users"})
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> read() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{pseudo}/{password}")
    public ResponseEntity<String> readByPseudoAndPassword(@PathVariable String pseudo, @PathVariable String password) {
        return new ResponseEntity<>(userService.getUserByPseudoAndPassword(pseudo, password), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

    @GetMapping("/pseudo/{pseudo}")
    public ResponseEntity<User> readById(@PathVariable String pseudo) {
        return new ResponseEntity<>(userService.getUserByPseudo(pseudo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User user) {
        return new ResponseEntity<>(userService.add(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user) {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userService.remove(id);
        return "Operation OK";
    }
}
