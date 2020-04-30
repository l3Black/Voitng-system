package ru.javawebinar.votingsystem.to;

import ru.javawebinar.votingsystem.model.Dish;

import java.util.List;

public class Restaurant {
    private final Integer id;
    private final String name;
    private final List<Dish> menu;

    public Restaurant(Integer id, String name, List<Dish> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                '}';
    }
}
