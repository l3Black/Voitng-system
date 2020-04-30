package ru.javawebinar.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.votingsystem.model.Restaurant;

import java.time.LocalDate;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

    Iterable<Restaurant> findAllByDate(LocalDate date);
}
