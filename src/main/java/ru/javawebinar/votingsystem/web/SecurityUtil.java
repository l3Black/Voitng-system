package ru.javawebinar.votingsystem.web;

import ru.javawebinar.votingsystem.model.User;

public class SecurityUtil {

    private static User user = new User(100000, "User", "user@yandex.ru", "password");

    public static User authUser() {
        return user;
    }

    public static void setAuthUser(User user) {
        SecurityUtil.user = user;
    }
}