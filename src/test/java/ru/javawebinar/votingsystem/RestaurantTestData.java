package ru.javawebinar.votingsystem;

import ru.javawebinar.votingsystem.model.Dish;
import ru.javawebinar.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public final class RestaurantTestData {
    public final static Restaurant REST1 = new Restaurant(100004, "bk", LocalDate.of(2020, 6, 20));
    private final static Dish DISH1_1 = new Dish(100009, "burger", 230, REST1);
    private final static Dish DISH1_2 = new Dish(100010, "cezar", 279, REST1);
    private final static Dish DISH1_3 = new Dish(100011, "pepsi", 90, REST1);
    static {
        REST1.setDishes(List.of(DISH1_1, DISH1_2, DISH1_3));
    }

    public final static Restaurant REST2 = new Restaurant(100003, "mumu", LocalDate.of(2020, 6, 20));
    private final static Dish DISH2_1 = new Dish(100006, "borsch", 220, REST2);
    private final static Dish DISH2_2 = new Dish(100007, "kartoha", 300, REST2);
    private final static Dish DISH2_3 = new Dish(100008, "kompot", 70, REST2);
    static {
        REST2.setDishes(List.of(DISH2_1, DISH2_2, DISH2_3));
    }

    public final static Restaurant REST3 = new Restaurant(100005, "tanuki", LocalDate.of(2020, 6, 21));
    private final static Dish DISH3_1 = new Dish(100012, "fila", 380, REST3);
    private final static Dish DISH3_2 = new Dish(100013, "gunkan", 120, REST3);
    private final static Dish DISH3_3 = new Dish(100014, "sake", 130, REST3);
    static {
        REST3.setDishes(List.of(DISH3_1, DISH3_2, DISH3_3));
    }

    public static Restaurant getNew(){
        Restaurant restaurant = new Restaurant("mone");
        Dish dish1 = new Dish(null, "fuagra", 3000, restaurant);
        Dish dish2 = new Dish(null, "hamon", 1200, restaurant);
        Dish dish3 = new Dish(null, "shato de burdo", 2000, restaurant);
        restaurant.setDishes(List.of(dish1, dish2, dish3));
        return restaurant;
    }

    public static Restaurant getUpdated(){
        Restaurant updated = new Restaurant(REST2);
        updated.setName("Mu-Mu");
        updated.getDishes().get(0).setName("filadelfia");
        updated.getDishes().get(0).setPrice(420);
        updated.getDishes().get(1).setName("Gunkans");
        updated.getDishes().get(1).setPrice(240);
        updated.getDishes().get(2).setName("OrangeJuice");
        updated.getDishes().get(2).setPrice(100);
        return updated;
    }

}
