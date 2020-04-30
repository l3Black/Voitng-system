package ru.javawebinar.votingsystem.web;

import ru.javawebinar.votingsystem.model.User;

public class SecurityUtil {

    private static User user = new User();

    private SecurityUtil() {
    }

    public static User authUser() {
        return user;
    }

    public static void setAuthUser(User user) {
        SecurityUtil.user = user;
    }
}