package ru.javawebinar.votingsystem;

import ru.javawebinar.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

public final class VoteTestData {

    public final static Vote VOTE1 = new Vote(100015, UserTestData.USER1, RestaurantTestData.REST2,
            LocalDate.of(2020, 6, 20), LocalTime.of(10, 0, 0));
    public final static Vote VOTE2 = new Vote(100016, UserTestData.USER2, RestaurantTestData.REST2,
            LocalDate.of(2020, 6, 20), LocalTime.of(9, 0, 0));


    public static Vote getNew() {
        return new Vote(null, UserTestData.USER1, RestaurantTestData.REST1);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE2);
        updated.setRestaurant(RestaurantTestData.REST3);
        updated.setTime(LocalTime.of(10, 55, 33));
        return updated;
    }
}