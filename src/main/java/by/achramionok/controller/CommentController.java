package by.achramionok.controller;

import by.achramionok.model.Comment;
import by.achramionok.repository.CommentRepository;
import by.achramionok.repository.TaskRepository;
import by.achramionok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternUtils;
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
@RequestMapping(value = "comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    Authentication auth;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNewComment(@RequestBody Comment comment){
        commentRepository.save(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/save/task/{id}", method = RequestMethod.POST)
    public ResponseEntity<Comment> saveChanges(@RequestBody Comment newComment, @PathVariable String id){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Comment comment = new Comment(taskRepository.findById(Integer.valueOf(id)),
                userRepository.findByEmail(auth.getName()),
                userRepository.findByEmail(auth.getName()).getUsername(),
                newComment.getContent());
        commentRepository.save(comment);
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    @RequestMapping(value = "/update/", method = RequestMethod.PUT)
    public ResponseEntity<Collection<Comment>> updateComment(@RequestBody Comment changedComment){
        Comment comment = commentRepository.findById(changedComment.getId());
        if(comment == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            comment.update(changedComment);
            comment = commentRepository.save(comment);
            return  new ResponseEntity<Collection<Comment>>(commentRepository.findAll(),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Collection<Comment>> deleteById(@PathVariable String id){
        if(commentRepository.findById(Integer.valueOf(id)) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(Integer.valueOf(id));
        return new ResponseEntity<>(commentRepository.findAll(),HttpStatus.OK);
    }
}
