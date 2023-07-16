package com.trellolike.model.service.Implementation;

import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.Board;
import com.trellolike.model.model.User_Board;
import com.trellolike.model.repository.BoardRepository;
import com.trellolike.model.service.Interface.BoardService;
import com.trellolike.model.service.Interface.UserBoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    private UserBoardService userBoardService;

    @Override
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board get(Integer id) {
        if(boardRepository.existsById("" + id))
            return boardRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Board does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Board> getByUserId(Integer id) {
        return boardRepository.getBoardsByUserId(id);
    }

    @Override
    public Board add(Board board, Integer id_user) {
        if(board.getName() == null && board.getName().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        Board result = boardRepository.save(board);
        userBoardService.add(new User_Board(null, id_user, result.getId_board()));
        return result;
    }

    @Override
    public Board update(Integer id, Board newBoard) {
        Optional<Board> opBoard = boardRepository.findById("" + id);
        if(opBoard.isEmpty())
            throw new ApiRequestException("There is no board with this id.", HttpStatus.NOT_FOUND);
        Board board = opBoard.get();
        if(newBoard.getId_board() != null)
            throw new ApiRequestException("'id_board' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newBoard.getName() != null && !newBoard.getName().equals("")) {
            board.setName(newBoard.getName());
        }
        if(newBoard.getDescription() != null) {
            board.setDescription(newBoard.getDescription());
        }
        return boardRepository.save(board);
    }

    @Override
    public void remove(Integer id) {
        if(boardRepository.existsById("" + id))
            boardRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Board does not exist.", HttpStatus.NOT_FOUND);
    }
}
