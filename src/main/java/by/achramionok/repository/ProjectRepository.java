package by.achramionok.repository;

import by.achramionok.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kirill on 22.03.2017.
 */
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findById(Integer id);
    Project findByName(String name);

}
