package by.achramionok.controller;

import by.achramionok.model.User;
import by.achramionok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Kirill on 21.03.2017.
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Collection<User> getAll(){
        return userRepository.findAll();
    }
}
