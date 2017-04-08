package by.achramionok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kirill on 20.03.2017.
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_task", nullable = false)
    @JsonIgnore
    private Task task;

    @ManyToOne
//    @JoinTable(
//            name="User",
//            joinColumns = @JoinColumn
//    )
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User userComment;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;


    public void update(Comment comment){
        this.id = comment.getId();
        this.name = comment.getName();
        this.userComment = comment.getUser();
        this.content = comment.getName();
        this.task = comment.getTask();
    }

    public Comment(Task task, User userComment, String name, String content) {
        this.task = task;
        this.userComment = userComment;
        this.name = name;
        this.content = content;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return userComment;
    }

    public void setUser(User user) {
        this.userComment = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
