package com.trellolike.controller;

import com.trellolike.model.Member;
import com.trellolike.service.Interface.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/Members", "/members"})
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Member[]> read() {
        return new ResponseEntity<>(memberService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> readByIsbn(@PathVariable String id) {
        return new ResponseEntity<>(memberService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member) {
        return new ResponseEntity<>(memberService.add(member), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable String id, @RequestBody Member member) {
        return new ResponseEntity<>(memberService.update(id, member), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        memberService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
