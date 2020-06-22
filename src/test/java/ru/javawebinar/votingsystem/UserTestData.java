package ru.javawebinar.votingsystem;

import ru.javawebinar.votingsystem.model.User;

public class UserTestData {
    public static final User USER1 = new User(100000, "User", "user@yandex.ru", "password");
    public static final User USER2 = new User(100001, "User", "user@yandex.ru", "password");

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass");
    }

    public static User getUpdated() {
        User updated = new User(USER2);
        updated.setName("updName");
        updated.setEmail("user@gmail.com");
        updated.setPassword("2ewe3!!ASD)");
        return updated;
    }
}
