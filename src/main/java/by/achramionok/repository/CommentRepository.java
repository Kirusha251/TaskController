package by.achramionok.repository;

import by.achramionok.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kirill on 21.03.2017.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(Integer id);
    Comment findByName(String name);

    void deleteById(Integer id);
}
