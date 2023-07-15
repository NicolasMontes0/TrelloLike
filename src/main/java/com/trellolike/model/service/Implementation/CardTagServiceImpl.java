package com.trellolike.model.service.Implementation;

import com.trellolike.model.repository.CardTagRepository;
import com.trellolike.model.service.Interface.CardTagService;
import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.Card_Tag;
import com.trellolike.model.repository.CardRepository;
import com.trellolike.model.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardTagServiceImpl implements CardTagService {

    private CardTagRepository cardTagRepository;

    private CardRepository cardRepository;

    private TagRepository tagRepository;

    @Override
    public List<Card_Tag> getAll() {
        return cardTagRepository.findAll();
    }

    @Override
    public Card_Tag get(Integer id) {
        if(cardTagRepository.existsById("" + id))
            return cardTagRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Card_Tag does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public Card_Tag add(Card_Tag card_tag) {
        if(!cardRepository.existsById("" + card_tag.getId_card()))
            throw new ApiRequestException("There is no card with this id.", HttpStatus.NOT_FOUND);
        if(!tagRepository.existsById("" + card_tag.getId_tag()))
            throw new ApiRequestException("There is no tag with this id.", HttpStatus.NOT_FOUND);
        List<Card_Tag> l = cardTagRepository.findAll();
        l.forEach(card_tag1 -> {
            if(card_tag1.getId_tag().equals(card_tag.getId_tag()) && card_tag1.getId_card().equals(card_tag.getId_card()))
                throw new ApiRequestException("This card_tag is already exist.", HttpStatus.BAD_REQUEST);
        });
        return cardTagRepository.save(card_tag);
    }

    @Override
    public void remove(Integer id) {
        if(cardTagRepository.existsById("" + id))
            cardTagRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Card_Tag does not exist.", HttpStatus.NOT_FOUND);
    }
}
