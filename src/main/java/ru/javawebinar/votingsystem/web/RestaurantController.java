package ru.javawebinar.votingsystem.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.votingsystem.model.Restaurant;
import ru.javawebinar.votingsystem.repository.RestaurantRepo;
import ru.javawebinar.votingsystem.util.exception.NotFoundException;

import java.net.URI;
import java.time.LocalDate;

import static ru.javawebinar.votingsystem.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepo repository;

    public RestaurantController(RestaurantRepo repository) {
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        restaurant.getDishes().forEach(dish -> dish.setRestaurant(restaurant));
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurant/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("restaurant", id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable Integer id) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Restaurant restaurant) {
        repository.delete(restaurant);
    }

    @GetMapping("/history")
    public Iterable<Restaurant> getAll() {
        return repository.findAll();
    }

    @GetMapping
    public Iterable<Restaurant> getRestaurantsToday() {
        LocalDate today = LocalDate.now();
        return repository.findAllByDate(today);
    }
}
