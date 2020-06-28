package ru.javawebinar.votingsystem.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.votingsystem.RestaurantTestData;
import ru.javawebinar.votingsystem.TestMatcher;
import ru.javawebinar.votingsystem.TestUtil;
import ru.javawebinar.votingsystem.model.Dish;
import ru.javawebinar.votingsystem.model.Restaurant;
import ru.javawebinar.votingsystem.repository.RestaurantRepo;
import ru.javawebinar.votingsystem.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votingsystem.RestaurantTestData.*;

public class RestaurantControllerTest extends AbstractControllerTest {

    public static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantRepo repository;

    private final static TestMatcher<Restaurant> matcherRest = TestMatcher.usingFieldsComparator(Restaurant.class, "dishes");
    private final static TestMatcher<Dish> matcherDish = TestMatcher.usingFieldsComparator(Dish.class, "restaurant");

    @Test
    public void create() throws Exception {
        Restaurant newRest = RestaurantTestData.getNew();
        ResultActions action = mvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRest)))
                .andExpect(status().isCreated());
        Restaurant created = TestUtil.readFromJson(action, Restaurant.class);
        int newRestId = created.getId();
        newRest.setId(newRestId);
        for (int i = 0; i < created.getDishes().size(); i++) {
            int dishId = created.getDishes().get(i).getId();
            newRest.getDishes().get(i).setId(dishId);
        }
        matcherRest.assertMatch(created, newRest);
        matcherRest.assertMatch(repository.findById(newRestId).get(), newRest);
        matcherDish.assertMatch(created.getDishes(), newRest.getDishes());
    }

    @Test
    public void get() throws Exception {
        ResultActions action = mvc.perform(MockMvcRequestBuilders.get(REST_URL + REST1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcherRest.contentJson(REST1));
        Restaurant result = TestUtil.readFromJson(action, Restaurant.class);
        matcherDish.assertMatch(result.getDishes(), REST1.getDishes());
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        mvc.perform(MockMvcRequestBuilders.put(REST_URL + REST2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Restaurant actual = repository.findById(REST2.getId()).get();
        matcherRest.assertMatch(actual, updated);
        matcherDish.assertMatch(actual.getDishes(), updated.getDishes());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(REST_URL + REST3.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        matcherRest.assertMatch(repository.findAll(), REST2, REST1);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> expected = List.of(REST2, REST1, REST3);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(REST_URL + "history"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcherRest.contentJson(expected))
                .andReturn();
        List<Restaurant> restActual = TestUtil.readListFromJsonMvcResult(result, Restaurant.class);
        for (int i = 0; i < restActual.size(); i++){
            matcherDish.assertMatch(restActual.get(i).getDishes(), expected.get(i).getDishes());
        }
    }

    @Test
    public void getRestaurantsByDate() throws Exception {
        List<Restaurant> expected = List.of(REST1, REST2);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .param("date", "2020-06-20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcherRest.contentJson(expected))
                .andReturn();
        List<Restaurant> restActual = TestUtil.readListFromJsonMvcResult(result, Restaurant.class);
        for (int i = 0; i < restActual.size(); i++){
            matcherDish.assertMatch(restActual.get(i).getDishes(), expected.get(i).getDishes());
        }
    }
}