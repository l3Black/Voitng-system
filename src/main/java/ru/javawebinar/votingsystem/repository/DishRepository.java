package ru.javawebinar.votingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javawebinar.votingsystem.model.Dish;

public interface DishRepository extends CrudRepository<Dish, Integer> {
}
