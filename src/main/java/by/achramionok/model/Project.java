package by.achramionok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Kirill on 20.03.2017.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Set<Task> tasks;

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns =
            @JoinColumn(name = "id_project",referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "id_user", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<User> users;

    public void update(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.createdAt = project.getCreatedAt();
        this.users = project.getUsers();
    }

    public Project() {

    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
