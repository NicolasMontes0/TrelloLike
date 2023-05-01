package com.trellolike.service.Interface;

import com.trellolike.model.Member;

import java.util.List;

public interface MemberService {

    List<Member> getAll();

    Member get(Integer id) throws Error;

    Member add(Member member);

    Member update(Integer id, Member member);

    void remove(Integer id) throws Error;
}
