package ru.javawebinar.votingsystem.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.votingsystem.model.Restaurant;
import ru.javawebinar.votingsystem.model.Vote;
import ru.javawebinar.votingsystem.repository.RestaurantRepo;
import ru.javawebinar.votingsystem.repository.VoteRepo;
import ru.javawebinar.votingsystem.to.VoteTo;
import ru.javawebinar.votingsystem.util.exception.CannotChangeVoteException;
import ru.javawebinar.votingsystem.util.exception.NotFoundException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {

    public static final String REST_URL = "/votes";
    final static LocalTime LAST_TIME_TO_CHANGE_VOTE = LocalTime.of(11, 0, 0);
    private final VoteRepo voteRepo;
    private final RestaurantRepo restRepo;

    public VoteController(VoteRepo voteRepo, RestaurantRepo restRepo) {
        this.voteRepo = voteRepo;
        this.restRepo = restRepo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@RequestBody Restaurant restaurant) {
        Objects.requireNonNull(restRepo.getOne(restaurant.getId()), "Not found restaurant: " + restaurant);
        Vote vote = voteRepo.findByRestaurantAndUser(restaurant, SecurityUtil.authUser());
        if (vote == null) {
            vote = voteRepo.save(new Vote(SecurityUtil.authUser(), restaurant));
        } else {
            throw new CannotChangeVoteException("vote with restaurant id = " + restaurant.getId() + "already exist");
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/votes/{id}")
                .buildAndExpand(vote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable int id, @RequestBody Restaurant restaurant) {
        Objects.requireNonNull(restRepo.getOne(restaurant.getId()), "Not found restaurant: " + restaurant);
        LocalTime voteTime = LocalTime.now();
        Vote vote = voteRepo.findById(id).orElseThrow(() -> new NotFoundException("Vote", id));
        if (voteTime.isBefore(LAST_TIME_TO_CHANGE_VOTE)) {
            vote.setRestaurant(restaurant);
            vote.setTime(voteTime);
            voteRepo.save(vote);
        } else
            throw new CannotChangeVoteException();
    }

    @GetMapping
    public Collection<VoteTo> getVotes() {
        return voteRepo.findAllByUser(SecurityUtil.authUser()).stream()
                .map(VoteTo::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/result")
    public Collection<VoteTo> getVotesToday() {
        LocalDate today = LocalDate.now();
        return voteRepo.findAllByDate(today).stream()
                .map(VoteTo::new)
                .collect(Collectors.toList());
    }

}
