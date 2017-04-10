package by.achramionok.service;

import by.achramionok.email.VerificationToken;
import by.achramionok.model.User;

/**
 * Created by Kirill on 10.04.2017.
 */
public interface IUserService {

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
