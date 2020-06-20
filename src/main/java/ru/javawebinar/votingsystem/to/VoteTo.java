package ru.javawebinar.votingsystem.to;

import ru.javawebinar.votingsystem.model.Vote;

import java.time.LocalDateTime;

public class VoteTo {
    private final int id;
    private final String userName;
    private final String userEmail;
    private final int restaurantId;
    private final String restaurantName;
    private final LocalDateTime date;

    public VoteTo(Vote vote) {
        this.id = vote.getId();
        this.userName = vote.getUser().getName();
        this.userEmail = vote.getUser().getEmail();
        this.restaurantId = vote.getRestaurant().getId();
        this.restaurantName = vote.getRestaurant().getName();
        this.date = LocalDateTime.of(vote.getDate(), vote.getTime());
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
