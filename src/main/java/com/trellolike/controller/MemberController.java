package com.trellolike.controller;

import com.trellolike.model.User;
import com.trellolike.service.Interface.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/Members", "/members"})
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<User>> read() {
        return new ResponseEntity<>(memberService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(memberService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<>(memberService.add(user), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user) {
        return new ResponseEntity<>(memberService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        memberService.remove(id);
        return "Operation OK";
    }
}
