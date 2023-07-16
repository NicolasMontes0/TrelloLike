package com.trellolike.model.service.Implementation;

import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.User;
import com.trellolike.model.repository.UserRepository;
import com.trellolike.model.service.Interface.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

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
            throw new ApiRequestException("This user does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public String getUserByPseudoAndPassword(String pseudo, String password) {
        return userRepository.getUser(pseudo, password);
    }

    @Override
    public User getUserByPseudo(String pseudo) {
        return userRepository.getUserByPseudo(pseudo);
    }

    @Override
    public String add(User user) {
        if(user.getPseudo() == null || user.getPseudo().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        if(user.getMail() == null || user.getMail().equals(""))
            throw new ApiRequestException("'mail' must not be null or blank.", HttpStatus.BAD_REQUEST);
        if(user.getPassword() == null || user.getPassword().equals(""))
            throw new ApiRequestException("'password' must not be null or blank.", HttpStatus.BAD_REQUEST);
        return userRepository.save(user).getId_user() + "";
    }

    @Override
    public User update(Integer id, User newUser) {
        Optional<User> opMember = userRepository.findById("" + id);
        if(opMember.isEmpty())
            throw new ApiRequestException("There is no user with this id.", HttpStatus.NOT_FOUND);
        User user = opMember.get();
        if(newUser.getId_user() != null || newUser.getPseudo().equals(""))
            throw new ApiRequestException("'id_user' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newUser.getPseudo() != null) {
            user.setPseudo(newUser.getPseudo());
        }
        if(newUser.getMail() != null && !newUser.getMail().equals("")) {
            user.setMail(newUser.getMail());
        }
        if(newUser.getPassword() != null && !newUser.getPassword().equals("")) {
            user.setPassword(newUser.getPassword());
        }
        return userRepository.save(user);
    }

    @Override
    public void remove(Integer id) {
        if(userRepository.existsById("" + id))
            userRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This user does not exist.", HttpStatus.NOT_FOUND);
    }
}
