package ru.javawebinar.votingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javawebinar.votingsystem.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
