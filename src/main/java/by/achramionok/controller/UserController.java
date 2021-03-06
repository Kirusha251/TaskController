package by.achramionok.controller;

import by.achramionok.authentication.RegistrationCredentials;
import by.achramionok.email.OnRegistrationCompleteEvent;
import by.achramionok.model.Comment;
import by.achramionok.model.Project;
import by.achramionok.model.Task;
import by.achramionok.model.User;
import by.achramionok.repository.ProjectRepository;
import by.achramionok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    Authentication auth;
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(value = "/signed", method = RequestMethod.GET)
    public ResponseEntity<User> getSignedUser() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable String id) {
        User user = userRepository.findById(Integer.valueOf(id));
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/all-developers", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllDevelopers(){
        return new ResponseEntity<Collection<User>>(userRepository.findAllByRole(2), HttpStatus.OK);
    }

    @RequestMapping(value = "/email/{email}/", method = RequestMethod.GET)
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(HttpServletRequest request, @RequestBody RegistrationCredentials u) {
        User user = userRepository.findByEmail(u.getEmail());
        if(userRepository.findByEmail(u.getEmail())== null &&
                userRepository.findByUserName(u.getUsername()) ==null){
            user = new User();
            user.setUsername(u.getUsername());
            user.setEmail(u.getEmail());
            user.setPassword(u.getPassword());
            user.setRole(u.getRole());
            user = userRepository.save(user);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/free-dev/{id}", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getFreeDevelopers(@PathVariable String id){
        Collection<User> devs= userRepository.findAllByRole(2);
        Project currentProject = projectRepository.findById(Integer.valueOf(id));
        Collection<User> freeDevs = new ArrayList<User>();
        for (User u:
             devs) {
            if(!u.getProjects().contains(currentProject)){
                freeDevs.add(u);
            }
        }
        return new ResponseEntity<Collection<User>>(freeDevs,
                HttpStatus.OK);
    }

}
