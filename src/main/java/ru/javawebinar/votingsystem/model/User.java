package ru.javawebinar.votingsystem.model;

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

}
