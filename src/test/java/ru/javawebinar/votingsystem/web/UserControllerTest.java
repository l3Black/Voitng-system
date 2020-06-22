package ru.javawebinar.votingsystem.web;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.votingsystem.JsonUtil;
import ru.javawebinar.votingsystem.TestMatcher;
import ru.javawebinar.votingsystem.TestUtil;
import ru.javawebinar.votingsystem.UserTestData;
import ru.javawebinar.votingsystem.model.User;
import ru.javawebinar.votingsystem.repository.UserRepository;
import ru.javawebinar.votingsystem.to.UserTo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votingsystem.UserTestData.USER1;
import static ru.javawebinar.votingsystem.UserTestData.USER2;


public class UserControllerTest extends AbstractControllerTest {

    private final String REST_URL = UserController.REST_URL + '/';
    private final TestMatcher<UserTo> matcherTo = TestMatcher.usingFieldsComparator(UserTo.class, "registered");
    private final TestMatcher<User> matcherUser = TestMatcher.usingFieldsComparator(User.class, "registered", "votes");
    @Autowired
    private UserRepository repository;

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(REST_URL + USER1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcherTo.contentJson(new UserTo(USER1)));
    }

    @Test
    public void create() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = mvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        int newId = created.getId();
        newUser.setId(newId);
        matcherUser.assertMatch(created, newUser);
        matcherUser.assertMatch(repository.findById(newId).get(), created);
    }

    @Test
    public void update() throws Exception {
        User updated = UserTestData.getUpdated();
        mvc.perform(MockMvcRequestBuilders.put(REST_URL + USER2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        matcherUser.assertMatch(repository.findById(USER2.getId()).get(), updated);
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(REST_URL + USER1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertFalse(repository.findById(USER1.getId()).isPresent());
    }
}