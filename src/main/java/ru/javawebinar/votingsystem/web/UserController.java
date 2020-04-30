package ru.javawebinar.votingsystem.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.votingsystem.model.User;
import ru.javawebinar.votingsystem.repository.UserRepository;
import ru.javawebinar.votingsystem.util.exception.NotFoundException;

import java.net.URI;

import static ru.javawebinar.votingsystem.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("user", id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create(@RequestBody User user) {
        checkNew(user);
        User created = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable Integer id) {
        Assert.notNull(user, "User must not be null");
        assureIdConsistent(user, id);
        repository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") User user) {
        repository.delete(user);
    }
}
