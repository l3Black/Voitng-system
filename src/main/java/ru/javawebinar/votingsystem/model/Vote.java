package ru.javawebinar.votingsystem.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Vote extends AbstractBaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "date")
    private LocalDate date;
}
