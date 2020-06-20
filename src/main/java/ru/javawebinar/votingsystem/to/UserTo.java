package ru.javawebinar.votingsystem.to;

import ru.javawebinar.votingsystem.model.User;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailL() {
        return email;
    }

    public LocalDate getRegistered() {
        return registered;
    }
}
