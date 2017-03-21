package by.achramionok.model;

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

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns =
            @JoinColumn(name = "id_project",referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "id_user", referencedColumnName = "id")
    )
    private Set<User> users;

    public Project() {

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
