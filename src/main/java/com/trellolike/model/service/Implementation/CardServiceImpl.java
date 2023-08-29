package com.trellolike.model.service.Implementation;

import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.Card;
import com.trellolike.model.repository.CardRepository;
import com.trellolike.model.repository.ListRepository;
import com.trellolike.model.service.Interface.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    private ListRepository listRepository;

    @Override
    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card get(Integer id) throws Error {
        if(cardRepository.existsById("" + id))
            return cardRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Card does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Card> getByIdList(Integer id) {
        return cardRepository.findByIdList(id);
    }

    @Override
    public Card add(Card card) {
        if(card.getName() == null || card.getName().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        if(card.getId_list() == null)
            throw new ApiRequestException("'id_card' must not be null or blank.", HttpStatus.BAD_REQUEST);
        if(!listRepository.existsById("" + card.getId_list()))
            throw new ApiRequestException("'id_list' does not exist.", HttpStatus.BAD_REQUEST);
        return cardRepository.save(card);
    }

    @Override
    public Card update(Integer id, Card newCard) {
        Optional<Card> opCard = cardRepository.findById("" + id);
        if(opCard.isEmpty())
            throw new ApiRequestException("There is no card with this id.", HttpStatus.NOT_FOUND);
        Card card = opCard.get();
        if(newCard.getId_card() != null)
            throw new ApiRequestException("'id_card' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newCard.getName() != null && !newCard.getName().equals("")) {
            card.setName(newCard.getName());
        }
        if(newCard.getDescription() != null) {
            card.setDescription(newCard.getDescription());
        }
        if(newCard.getDate() != null) {
            card.setDate(newCard.getDate());
        }
        if(newCard.getId_list() != null) {
            if(!listRepository.existsById("" + newCard.getId_list()))
                throw new ApiRequestException("'id_List' does not exist.", HttpStatus.BAD_REQUEST);
            card.setId_list(newCard.getId_list());
        }
        return cardRepository.save(card);
    }

    @Override
    public void remove(Integer id) {
        if(cardRepository.existsById("" + id))
            cardRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Card does not exist.", HttpStatus.NOT_FOUND);
    }
}
