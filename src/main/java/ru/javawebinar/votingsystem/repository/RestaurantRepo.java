package ru.javawebinar.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByDateOrderByName(LocalDate date);
}
