package ru.javawebinar.votingsystem.web;

import ru.javawebinar.votingsystem.model.User;

public class SecurityUtil {

    private static User user = new User("User", "user@yandex.ru", "password");

    public static User authUser() {
        if (user.getId() == null) {
            user.setId(100000);
        }
        return user;
    }

    public static void setAuthUser(User user) {
        SecurityUtil.user = user;
    }
}