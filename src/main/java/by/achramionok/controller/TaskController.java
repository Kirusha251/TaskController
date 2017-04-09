package by.achramionok.controller;

import by.achramionok.model.Comment;
import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.model.User;
import by.achramionok.repository.ProjectRepository;
import by.achramionok.repository.TaskRepository;
import by.achramionok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    Authentication auth;
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<Collection<Task>> getAll(){
        return new ResponseEntity<Collection<Task>>(taskRepository.findAll(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/all/developer/{id}",method = RequestMethod.GET)
    public ResponseEntity<Collection<Task>> getDeveloperTasks(@PathVariable String id){
        auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<Collection<Task>>(
                taskRepository.findAllByProjectAndUserTask(projectRepository.findById(Integer.valueOf(id)),
                userRepository.findByEmail(auth.getName())),
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

    @RequestMapping(value = "user/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByTaskId(@PathVariable String id){
        Task task = taskRepository.findById(Integer.valueOf(id));
        if(task == null){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(task.getUser(),HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Task> getByName(@PathVariable String name){
        Task task = taskRepository.findByName(name);
        if(task == null){
            return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/add/by-project-id/{id}/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Task> addNewTask(@RequestBody Task task, @PathVariable String id, @PathVariable String userId){
        User user = userRepository.findById(Integer.valueOf(userId));
        Project project = projectRepository.findById(Integer.valueOf(id));
        Task newTask =new Task(task.getName(),
                task.getStatus(),
                task.getDescription(),
                project, user);
        if(user != null){
            taskRepository.save(newTask);
            return new ResponseEntity<Task>(newTask, HttpStatus.OK);
        }else{
            return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable String id){
        taskRepository.delete(Integer.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@RequestBody Task task,@PathVariable String id){
        Task task1 = taskRepository.findById(task.getId());
        if(task1 == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        task1.update(task.getName(), task.getStatus(), task.getDescription());
        if(Integer.valueOf(id)!=0) {
            task1.setUser(userRepository.findById(Integer.valueOf(id)));
        }
        taskRepository.save(task1);
        return new ResponseEntity<>(task1,HttpStatus.OK);
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
