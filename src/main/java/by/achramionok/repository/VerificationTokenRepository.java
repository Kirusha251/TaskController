package by.achramionok.repository;

import by.achramionok.email.VerificationToken;
import by.achramionok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kirill on 10.04.2017.
 */
public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
