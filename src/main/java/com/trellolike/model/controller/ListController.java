package com.trellolike.model.controller;

import com.trellolike.model.model.List;
import com.trellolike.model.service.Interface.ListService;
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
    public ResponseEntity<java.util.List<List>> read() {
        return new ResponseEntity<>(listService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(listService.get(id), HttpStatus.OK);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<java.util.List<List>> readByProject(@PathVariable Integer id) {
        return new ResponseEntity<>(listService.getByProject(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List> create(@RequestBody List list) {
        return new ResponseEntity<>(listService.add(list), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List> update(@PathVariable Integer id, @RequestBody List list) {
        return new ResponseEntity<>(listService.update(id, list), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        listService.remove(id);
        return "Operation OK";
    }
}