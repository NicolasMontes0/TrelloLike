package com.trellolike.controller;

import com.trellolike.model.Tag;
import com.trellolike.service.Interface.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/Tags", "/tags"})
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Tag[]> read() {
        return new ResponseEntity<>(tagService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> readByIsbn(@PathVariable String id) {
        return new ResponseEntity<>(tagService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        return new ResponseEntity<>(tagService.add(tag), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> update(@PathVariable String id, @RequestBody Tag tag) {
        return new ResponseEntity<>(tagService.update(id, tag), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        tagService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
