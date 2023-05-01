package com.trellolike.service.Implementation;

import com.trellolike.exception.ApiRequestException;
import com.trellolike.model.Member;
import com.trellolike.repository.MemberRepository;
import com.trellolike.service.Interface.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Override
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member get(Integer id) {
        if(memberRepository.existsById("" + id))
            return memberRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Member does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public Member add(Member member) {
        if(member.getName() == null || member.getName().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        return memberRepository.save(member);
    }

    @Override
    public Member update(Integer id, Member newMember) {
        Optional<Member> opMember = memberRepository.findById("" + id);
        if(opMember.isEmpty())
            throw new ApiRequestException("There is no member with this isbn.", HttpStatus.NOT_FOUND);
        Member member = opMember.get();
        if(newMember.getId_member() != null || newMember.getName().equals(""))
            throw new ApiRequestException("'id_member' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newMember.getName() != null) {
            member.setName(newMember.getName());
        }
        return memberRepository.save(member);
    }

    @Override
    public void remove(Integer id) {
        if(memberRepository.existsById("" + id))
            memberRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Member does not exist.", HttpStatus.NOT_FOUND);
    }
}
