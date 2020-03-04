package ru.javawebinar.votingsystem.model;

import javax.persistence.*;

@Entity
public class Dish extends AbstractNamedEntity {
    @Column(name = "price")
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
