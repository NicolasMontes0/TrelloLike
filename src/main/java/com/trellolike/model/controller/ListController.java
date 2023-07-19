package com.trellolike.model.controller;

import com.trellolike.model.model.ListModel;
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
    public ResponseEntity<java.util.List<ListModel>> read() {
        return new ResponseEntity<>(listService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListModel> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(listService.get(id), HttpStatus.OK);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<java.util.List<ListModel>> readByProject(@PathVariable Integer id) {
        return new ResponseEntity<>(listService.getByProject(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListModel> create(@RequestBody ListModel list) {
        return new ResponseEntity<>(listService.add(list), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListModel> update(@PathVariable Integer id, @RequestBody ListModel list) {
        return new ResponseEntity<>(listService.update(id, list), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        listService.remove(id);
        return "Operation OK";
    }
}