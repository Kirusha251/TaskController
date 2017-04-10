package by.achramionok.service.impl;

import by.achramionok.email.VerificationToken;
import by.achramionok.model.User;
import by.achramionok.repository.UserRepository;
import by.achramionok.repository.VerificationTokenRepository;
import by.achramionok.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kirill on 10.04.2017.
 */
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private VerificationTokenRepository tokenRepository;


    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }


    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }


    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}
