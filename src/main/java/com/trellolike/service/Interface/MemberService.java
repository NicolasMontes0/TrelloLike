package com.trellolike.service.Interface;

import com.trellolike.model.Card;
import com.trellolike.model.Member;

public interface MemberService {

    Member[] getAll();

    Member get(String id);

    Member add(Member member);

    Member update(String id, Member member);

    void remove(String id);
}
