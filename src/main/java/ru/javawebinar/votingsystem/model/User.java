package ru.javawebinar.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends AbstractNamedEntity {

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private final LocalDate registered = LocalDate.now();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vote> votes;

    public User() {
    }

    public User(Integer id, String name) {
        super(id, name);
    }

    public User(String name, @Email String email, String password) {
        this(null, name);
        this.email = email;
        this.password = password;
        this.role = Role.ROLE_USER;
    }
    //setters


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public List<Vote> getVotes() {
        return votes;
    }
}
