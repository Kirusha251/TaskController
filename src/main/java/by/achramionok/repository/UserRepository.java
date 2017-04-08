package by.achramionok.repository;

import by.achramionok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Kirill on 21.03.2017.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findById(Integer idUser);
    User findByEmail(String email);
    User findByUserName(String UserName);
    Collection<User> findAllByRole(Integer role);
    Set<User> findAllByRoleAndProjectsId(Integer role,Integer id);
    //Set<User> findAllByRole(Integer id,Integer role);
//    @Query("SELECT u.id, u.userName, u.role FROM User u CROSS JOIN Project p ON p.id <> :id and u.role = :role")
//    Set<User> findFreeUsers(@Param("id") Integer id, @Param("role") Integer role);
    void delete(Integer idUser);
}
