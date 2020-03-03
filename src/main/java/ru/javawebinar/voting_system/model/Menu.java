package ru.javawebinar.voting_system.model;

import java.time.LocalDate;
import java.util.Map;

public class Menu extends AbstractBaseEntity {
    private String restaurant;
    private LocalDate date;
    private Map<String, Integer> menu;
}
