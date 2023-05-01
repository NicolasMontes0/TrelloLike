package com.trellolike.controller;

import com.trellolike.model.Card;
import com.trellolike.service.Interface.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/Cards", "/cards"})
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<Card>> read() {
        return new ResponseEntity<>(cardService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(cardService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody Card card) {
        return new ResponseEntity<>(cardService.add(card), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Card> update(@PathVariable Integer id, @RequestBody Card card) {
        return new ResponseEntity<>(cardService.update(id, card), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        cardService.remove(id);
        return "Operation OK";
    }
}
