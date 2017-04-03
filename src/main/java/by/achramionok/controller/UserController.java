package by.achramionok.controller;

import by.achramionok.model.Comment;
import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.model.User;
import by.achramionok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.ws.Response;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Kirill on 21.03.2017.
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable String id) {
        User user = userRepository.findById(Integer.valueOf(id));
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getByUserName(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseEntity save(@RequestBody User newUser) {
        User user = userRepository.findById(newUser.getId());
        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            user.update(newUser);
            userRepository.save(user);
            return ResponseEntity.ok("OK");
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        userRepository.delete(Integer.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Task>> getUserTasks(@PathVariable String id) {
        User user = userRepository.findById(Integer.valueOf(id));
        if (user == null) {
            return new ResponseEntity<Set<Task>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<Task>>(user.getTasks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Comment>> getUserComments(@PathVariable String id) {
        User user = userRepository.findById(Integer.valueOf(id));
        if (user == null) {
            return new ResponseEntity<Set<Comment>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<Comment>>(user.getComments(), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Project>> getUserProjects(@PathVariable String id) {
        User user = userRepository.findById(Integer.valueOf(id));
        if (user == null) {
            return new ResponseEntity<Set<Project>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Set<Project>>(user.getProjects(), HttpStatus.OK);
    }

    @RequestMapping(value = "/project-dev/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<User>> getDevelopersFromProject(@PathVariable String id){
        return new ResponseEntity<Set<User>>(userRepository.findAllByRoleAndProjectsId(2,Integer.valueOf(id)),
                HttpStatus.OK);
    }

//    @RequestMapping(value = "/free-dev/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Set<User>> getFreeDevelopers(@PathVariable String id){
//
//        return new ResponseEntity<Set<User>>(userRepository.findAllByProjectsIdNotAndRole(1,2),
//                HttpStatus.OK);
//    }

}
