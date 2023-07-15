package com.trellolike.model.controller;

import com.trellolike.model.model.Card_Tag;
import com.trellolike.model.service.Interface.CardTagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/CardsTags", "/cardstags"})
@AllArgsConstructor
public class CardTagController {

    private final CardTagService card_TagService;

    @GetMapping
    public ResponseEntity<java.util.List<Card_Tag>> read() {
        return new ResponseEntity<>(card_TagService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card_Tag> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(card_TagService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Card_Tag> create(@RequestBody Card_Tag card_tag) {
        return new ResponseEntity<>(card_TagService.add(card_tag), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        card_TagService.remove(id);
        return "Operation OK";
    }
}
