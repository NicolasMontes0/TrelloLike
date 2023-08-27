package com.trellolike.model.service.Implementation;

import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.Card;
import com.trellolike.model.model.ListModel;
import com.trellolike.model.repository.BoardRepository;
import com.trellolike.model.repository.CardRepository;
import com.trellolike.model.repository.ListRepository;
import com.trellolike.model.service.Interface.ListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListServiceImpl implements ListService {

    private ListRepository listRepository;

    private BoardRepository boardRepository;

    private CardRepository cardRepository;

    @Override
    public java.util.List<ListModel> getAll() {
        return listRepository.findAll();
    }

    @Override
    public ListModel get(Integer id) {
        if(listRepository.existsById("" + id))
            return listRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This List does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public java.util.List<ListModel> getByProject(Integer id) {
        return listRepository.getByProject(id);
    }

    @Override
    public ListModel add(ListModel list) {
        if(list.getName() == null || list.getName().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        if(!boardRepository.existsById("" + list.getId_board()))
            throw new ApiRequestException("'id_board' does not exist.", HttpStatus.BAD_REQUEST);
        return listRepository.save(list);
    }

    @Override
    public ListModel update(Integer id, ListModel newList) {
        Optional<ListModel> opList = listRepository.findById("" + id);
        if(opList.isEmpty())
            throw new ApiRequestException("There is no list with this id.", HttpStatus.NOT_FOUND);
        ListModel list = opList.get();
        if(newList.getId_list() != null)
            throw new ApiRequestException("'id_list' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newList.getName() != null && !newList.getName().equals(""))
            list.setName(newList.getName());
        if(newList.getId_board() != null)
            throw new ApiRequestException("'id_board' cannot be changed.", HttpStatus.BAD_REQUEST);
        return listRepository.save(list);
    }

    @Override
    public void remove(Integer id) {
        if(listRepository.existsById("" + id)) {
            List<Card> cards = cardRepository.findByIdList(id);
            for (Card card : cards) {
                cardRepository.deleteById("" + card.getId_card());
            }
            listRepository.deleteById("" + id);
        }
        else
            throw new ApiRequestException("This List does not exist.", HttpStatus.NOT_FOUND);
    }
}
