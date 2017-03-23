package by.achramionok.controller;

import by.achramionok.model.Comment;
import by.achramionok.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Kirill on 22.03.2017.
 */
@RestController
@RequestMapping(value = "comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNewComment(@RequestBody Comment comment){
        commentRepository.save(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseEntity saveChanges(@RequestBody Comment newComment){
        Comment comment = commentRepository.findById(newComment.getId());
        if(comment == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        comment.update(newComment);
        commentRepository.save(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable String id){
        if(commentRepository.findById(Integer.valueOf(id)) == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(Integer.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
