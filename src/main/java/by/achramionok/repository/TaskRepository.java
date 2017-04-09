package by.achramionok.repository;

import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Kirill on 21.03.2017.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findById(Integer id);
    Task findByName(String name);
    Collection<Task> findAllByProjectAndUserTask(Project project, User user);
    void delete(Integer id);
}
