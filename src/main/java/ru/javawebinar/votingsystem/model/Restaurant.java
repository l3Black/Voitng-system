package ru.javawebinar.votingsystem.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    private LocalDate date = LocalDate.now();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @OrderBy("name, price")
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(String name) {
        super(name);
    }

    public Restaurant(Integer id, String name, LocalDate date) {
        super(id, name);
        this.date = date;
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
        this.date = restaurant.getDate();
        this.dishes = restaurant.getDishes();
    }

    //Getters and Setters

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
