package ru.javawebinar.votingsystem.to;

import ru.javawebinar.votingsystem.model.User;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class UserTo {
    private final int id;
    private final String name;
    private final String email;
    private final LocalDate registered;

    public UserTo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.registered = user.getRegistered();
    }

    @ConstructorProperties({"id", "name", "email", "registered"})
    public UserTo(int id, String name, String email, LocalDate registered) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registered = registered;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistered() {
        return registered;
    }
}
