package by.achramionok.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Kirill on 20.03.2017.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userTask;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;

    public Task() {

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return userTask;
    }

    public void setUser(User user) {
        this.userTask = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
