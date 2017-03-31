package by.achramionok.controller;

import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Kirill on 22.03.2017.
 */
@RestController
@RequestMapping(value = "project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

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
    public ResponseEntity addNewProject(@RequestBody Project project) {
        if (projectRepository.findById(project.getId()) == null) {
            projectRepository.save(project);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseEntity saveChanges(@RequestBody Project newProject){
        Project project = projectRepository.findById(newProject.getId());
        if(project == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        project.update(newProject);
        projectRepository.save(project);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Task>> getAllTasks(@PathVariable String id){
        Project project = projectRepository.findById(Integer.valueOf(id));
        if(project == null){
            return new ResponseEntity<Set<Task>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<Task>>(project.getTasks(),
                HttpStatus.OK);
    }
}
