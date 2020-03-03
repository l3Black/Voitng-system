package ru.javawebinar.voting_system.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {
    private User user;
    private Menu menu;
    private LocalDate date;
}
