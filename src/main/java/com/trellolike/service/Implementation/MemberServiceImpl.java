package com.trellolike.service.Implementation;

import com.trellolike.exception.ApiRequestException;
import com.trellolike.model.User;
import com.trellolike.repository.UserRepository;
import com.trellolike.service.Interface.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Integer id) {
        if(userRepository.existsById("" + id))
            return userRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Member does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public User add(User user) {
        if(user.getName() == null || user.getName().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User newUser) {
        Optional<User> opMember = userRepository.findById("" + id);
        if(opMember.isEmpty())
            throw new ApiRequestException("There is no member with this id.", HttpStatus.NOT_FOUND);
        User user = opMember.get();
        if(newUser.getId_user() != null || newUser.getName().equals(""))
            throw new ApiRequestException("'id_member' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newUser.getName() != null) {
            user.setName(newUser.getName());
        }
        return userRepository.save(user);
    }

    @Override
    public void remove(Integer id) {
        if(userRepository.existsById("" + id))
            userRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Member does not exist.", HttpStatus.NOT_FOUND);
    }
}
