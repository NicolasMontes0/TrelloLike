package com.trellolike.service.Implementation;

import com.trellolike.exception.ApiRequestException;
import com.trellolike.model.Card_User;
import com.trellolike.repository.CardUserRepository;
import com.trellolike.repository.CardRepository;
import com.trellolike.repository.UserRepository;
import com.trellolike.service.Interface.CardMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardMemberServiceImpl implements CardMemberService {

    private CardUserRepository cardUserRepository;

    private CardRepository cardRepository;

    private UserRepository userRepository;

    @Override
    public List<Card_User> getAll() {
        return cardUserRepository.findAll();
    }

    @Override
    public Card_User get(Integer id) {
        if(cardUserRepository.existsById("" + id))
            return cardUserRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Card_Member does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public Card_User add(Card_User card_user) {
        if(!cardRepository.existsById("" + card_user.getId_card()))
            throw new ApiRequestException("There is no card with this id.", HttpStatus.NOT_FOUND);
        if(!userRepository.existsById("" + card_user.getId_user()))
            throw new ApiRequestException("There is no member with this id.", HttpStatus.NOT_FOUND);
        List<Card_User> l = cardUserRepository.findAll();
        l.forEach(card_member1 -> {
            if(card_member1.getId_user().equals(card_user.getId_user()) && card_member1.getId_card().equals(card_user.getId_card()))
                throw new ApiRequestException("This card_member is already exist.", HttpStatus.BAD_REQUEST);
        });
        return cardUserRepository.save(card_user);
    }

    @Override
    public void remove(Integer id) {
        if(cardUserRepository.existsById("" + id))
            cardUserRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Card_Member does not exist.", HttpStatus.NOT_FOUND);
    }
}
