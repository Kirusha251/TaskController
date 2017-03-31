package by.achramionok.controller;

import by.achramionok.model.Comment;
import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.repository.ProjectRepository;
import by.achramionok.repository.TaskRepository;
import javafx.geometry.Pos;
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
@RequestMapping(value = "task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<Collection<Task>> getAll(){
        return new ResponseEntity<Collection<Task>>(taskRepository.findAll(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getById(@PathVariable String id){
        Task task = taskRepository.findById(Integer.valueOf(id));
        if(task == null){
            return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Task>(task,HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Task> getByName(@PathVariable String name){
        Task task = taskRepository.findByName(name);
        if(task == null){
            return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNewTask(@RequestBody Task task){
        if(taskRepository.findById(task.getId())==null){
            taskRepository.save(task);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable String id){
        taskRepository.delete(Integer.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Comment>> getComments(@PathVariable String id){
        Task task = taskRepository.findById(Integer.valueOf(id));
        if (task == null) {
            return new ResponseEntity<Set<Comment>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<Comment>>(task.getComments(),
                HttpStatus.OK);
    }

}
