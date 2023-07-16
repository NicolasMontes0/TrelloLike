package com.trellolike.model.service.Implementation;

import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.User_Board;
import com.trellolike.model.repository.BoardRepository;
import com.trellolike.model.repository.UserBoardRepository;
import com.trellolike.model.repository.UserRepository;
import com.trellolike.model.service.Interface.UserBoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserBoardServiceImpl implements UserBoardService {

    private UserBoardRepository userBoardRepository;

    private BoardRepository boardRepository;

    private UserRepository userRepository;

    @Override
    public List<User_Board> getAll() {
        return userBoardRepository.findAll();
    }

    @Override
    public User_Board get(Integer id) {
        if(userBoardRepository.existsById("" + id))
            return userBoardRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This User_Board does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public User_Board add(User_Board user_board) {
        if(!boardRepository.existsById("" + user_board.getId_board()))
            throw new ApiRequestException("There is no board with this id.", HttpStatus.NOT_FOUND);
        if(!userRepository.existsById("" + user_board.getId_user()))
            throw new ApiRequestException("There is no user with this id.", HttpStatus.NOT_FOUND);
        List<User_Board> l = userBoardRepository.findAll();
        l.forEach(user_board1 -> {
            if(user_board1.getId_user().equals(user_board.getId_user()) && user_board1.getId_board().equals(user_board.getId_board()))
                throw new ApiRequestException("This user_board is already exist.", HttpStatus.BAD_REQUEST);
        });
        return userBoardRepository.save(user_board);
    }

    @Override
    public void remove(Integer id) {
        if(userBoardRepository.existsById("" + id))
            userBoardRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This User_Board does not exist.", HttpStatus.NOT_FOUND);
    }
}
