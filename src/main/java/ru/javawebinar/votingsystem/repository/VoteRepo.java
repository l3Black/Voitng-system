package ru.javawebinar.votingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javawebinar.votingsystem.model.Restaurant;
import ru.javawebinar.votingsystem.model.User;
import ru.javawebinar.votingsystem.model.Vote;

import java.time.LocalDate;

public interface VoteRepo extends CrudRepository<Vote, Integer> {

    Vote findByRestaurantAndUser(Restaurant rest, User user);

    Iterable<Vote> findAllByUser(User user);

    Iterable<Vote> findAllByDate(LocalDate date);
}
