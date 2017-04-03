package by.achramionok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Kirill on 20.03.2017.
 */
@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    @JsonIgnore
    private String email;

    @Column(name = "role")
    private int role;

    @OneToMany(mappedBy = "userComment")
    @JsonIgnore
    private Set<Comment> comments;

    @OneToMany(mappedBy = "userTask")
    @JsonIgnore
    private Set<Task> tasks;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Project> projects;

    public User() {

    }

    public void update(User newUser){
        this.userName = newUser.userName;
        this.email = newUser.email;
        this.password = newUser.password;
        this.comments = newUser.comments;
        this.tasks = newUser.tasks;
        this.projects = newUser.projects;
        this.role = newUser.role;
        this.id = newUser.id;
    }
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
