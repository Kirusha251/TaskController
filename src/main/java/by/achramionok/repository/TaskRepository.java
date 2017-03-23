package by.achramionok.repository;

import by.achramionok.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kirill on 21.03.2017.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findById(Integer id);
    Task findByName(String name);

    void delete(Integer id);
}
