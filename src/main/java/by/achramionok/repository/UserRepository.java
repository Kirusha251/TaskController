package by.achramionok.repository;

import by.achramionok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kirill on 21.03.2017.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findById(Integer idUser);
    User findByEmail(String email);
    User findByName(String name);

    void deleteById(Integer idUser);
}
