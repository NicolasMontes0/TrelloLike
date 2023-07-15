package com.trellolike.model.controller;

import com.trellolike.model.model.Board;
import com.trellolike.model.service.Interface.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/Boards", "/boards"})
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> read() {
        return new ResponseEntity<>(boardService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(boardService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Board> create(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.add(board), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> update(@PathVariable Integer id, @RequestBody Board board) {
        return new ResponseEntity<>(boardService.update(id, board), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        boardService.remove(id);
        return "Operation OK";
    }
}