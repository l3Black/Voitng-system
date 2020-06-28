package ru.javawebinar.votingsystem.to;

import ru.javawebinar.votingsystem.model.Vote;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

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
    @ConstructorProperties({"id", "userName", "userEmail", "restaurantId", "restaurantName", "date"})
    public VoteTo(int id, String userName, String userEmail, int restaurantId, String restaurantName, LocalDateTime date) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return id == voteTo.id &&
                restaurantId == voteTo.restaurantId &&
                userName.equals(voteTo.userName) &&
                userEmail.equals(voteTo.userEmail) &&
                restaurantName.equals(voteTo.restaurantName) &&
                date.equals(voteTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userEmail, restaurantId, restaurantName, date);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", date=" + date +
                '}';
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
