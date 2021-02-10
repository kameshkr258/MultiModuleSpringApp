package com.makeen.assignment.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
public class User {

    private Long id;

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;

    private List<LastUsedPassword> lastUsedPassword;

    public User() {
        super();
    }

    public User(Long id, String username, String password, String email,List<LastUsedPassword> lastUsedPassword) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastUsedPassword = lastUsedPassword;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @OneToMany(mappedBy = "userId")
    public List<LastUsedPassword> getLastUsedPassword() {
        return lastUsedPassword;
    }

    public void setLastUsedPassword(List<LastUsedPassword> lastUsedPassword) {
        this.lastUsedPassword = lastUsedPassword;
    }
}