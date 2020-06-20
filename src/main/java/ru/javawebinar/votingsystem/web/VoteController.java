package ru.javawebinar.votingsystem.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votingsystem.model.Restaurant;
import ru.javawebinar.votingsystem.model.Vote;
import ru.javawebinar.votingsystem.repository.RestaurantRepo;
import ru.javawebinar.votingsystem.repository.VoteRepo;
import ru.javawebinar.votingsystem.to.VoteTo;
import ru.javawebinar.votingsystem.util.exception.CannotChangeVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final static LocalTime LAST_TIME_TO_CHANGE_VOTE = LocalTime.of(11, 0, 0);
    private final VoteRepo voteRepo;
    private final RestaurantRepo restRepo;

    public VoteController(VoteRepo voteRepo, RestaurantRepo restRepo) {
        this.voteRepo = voteRepo;
        this.restRepo = restRepo;
    }

    @PutMapping("/{restId}")
    @ResponseStatus(HttpStatus.OK)
    public void vote(@PathVariable int restId) {
        Restaurant restaurant = restRepo.getOne(restId);
        Objects.requireNonNull(restaurant, "Not found restaurant with Id= " + restId);
        LocalTime voteTime = LocalTime.now();
        Vote vote = voteRepo.findByRestaurantAndUser(restaurant, SecurityUtil.authUser());
        if (vote == null) {
            voteRepo.save(new Vote(SecurityUtil.authUser(), restaurant));
        } else if (voteTime.isAfter(LAST_TIME_TO_CHANGE_VOTE)) {
            vote.setRestaurant(restaurant);
            vote.setTime(voteTime);
            voteRepo.save(vote);
        } else
            throw new CannotChangeVoteException();
    }

    @GetMapping()
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
