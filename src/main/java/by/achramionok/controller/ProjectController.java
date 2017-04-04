package by.achramionok.controller;

import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.model.User;
import by.achramionok.repository.ProjectRepository;
import by.achramionok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kirill on 22.03.2017.
 */
@RestController
@RequestMapping(value = "project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    Authentication auth;

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> getById(@PathVariable String id) {
        Project project = projectRepository.findById(Integer.valueOf(id));
        if (project == null) {
            return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Project> getByName(@PathVariable String name) {
        Project project = projectRepository.findByName(name);
        if (project == null) {
            return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Project>> getAll() {
        return new ResponseEntity<Collection<Project>>(projectRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable String id) {
        projectRepository.delete(Integer.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Project> addNewProject(@RequestBody Project proj) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Project project = null; //new Project(proj.getName(),proj.getCreatedAt());
        if (user != null) {
            projectRepository.save(new HashSet<Project>() {{
                add(new Project(proj.getName(), proj.getCreatedAt(), new HashSet<User>() {{
                    add(user);
                }}));
            }});
            return new ResponseEntity<Project>(projectRepository.findByName(proj.getName()), HttpStatus.OK);
        }
        return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/all/by-user", method = RequestMethod.GET)
    public ResponseEntity<Collection<Project>> getAllByUserId() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        if (user == null) {
            return new ResponseEntity<Collection<Project>>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRole() == 1) {
            return new ResponseEntity<Collection<Project>>(projectRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Collection<Project>>(user.getProjects(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseEntity saveChanges(@RequestBody Project newProject) {
        Project project = projectRepository.findById(newProject.getId());
        if (project == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        project.update(newProject);
        projectRepository.save(project);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Task>> getAllTasks(@PathVariable String id) {
        Project project = projectRepository.findById(Integer.valueOf(id));
        if (project == null) {
            return new ResponseEntity<Set<Task>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<Task>>(project.getTasks(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/developers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<User>> getFreeDevelopers(@PathVariable String id) {
        Project project = projectRepository.findById(Integer.valueOf(id));
        if (project == null) {
            return new ResponseEntity<Set<User>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<User>>(project.getUsers(),
                HttpStatus.OK);
    }
}
