package ru.javawebinar.votingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu extends AbstractBaseEntity {
    @Column(name = "restaurant")
    private String restaurant;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "menu")
    private List<Dish> dishes;
}
