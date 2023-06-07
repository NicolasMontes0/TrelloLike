package com.trellolike.controller;

import com.trellolike.model.Card_User;
import com.trellolike.service.Interface.CardMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/CardsMembers", "/cardsmembers"})
@AllArgsConstructor
public class CardMemberController {

    private final CardMemberService card_memberService;

    @GetMapping
    public ResponseEntity<java.util.List<Card_User>> read() {
        return new ResponseEntity<>(card_memberService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card_User> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(card_memberService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Card_User> create(@RequestBody Card_User card_user) {
        return new ResponseEntity<>(card_memberService.add(card_user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        card_memberService.remove(id);
        return "Operation OK";
    }
}
