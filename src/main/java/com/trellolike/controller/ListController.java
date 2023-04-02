package com.trellolike.controller;

import com.trellolike.model.List;
import com.trellolike.service.Interface.ListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/Lists", "/lists"})
@AllArgsConstructor
public class ListController {

    private final ListService listService;

    @GetMapping
    public ResponseEntity<List[]> read() {
        return new ResponseEntity<>(listService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List> readByIsbn(@PathVariable String id) {
        return new ResponseEntity<>(listService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List> create(@RequestBody List list) {
        return new ResponseEntity<>(listService.add(list), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<List> update(@PathVariable String id, @RequestBody List list) {
        return new ResponseEntity<>(listService.update(id, list), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        listService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}