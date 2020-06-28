package ru.javawebinar.votingsystem.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.votingsystem.*;
import ru.javawebinar.votingsystem.model.Vote;
import ru.javawebinar.votingsystem.repository.VoteRepo;
import ru.javawebinar.votingsystem.to.VoteTo;
import ru.javawebinar.votingsystem.util.ValidationUtil;
import ru.javawebinar.votingsystem.util.exception.CannotChangeVoteException;
import ru.javawebinar.votingsystem.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votingsystem.VoteTestData.VOTE1;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    private final TestMatcher<Vote> voteMatcher = TestMatcher.usingFieldsComparator(Vote.class, "date", "time");
    private final TestMatcher<VoteTo> toMatcher = TestMatcher.usingEquals(VoteTo.class);

    @Autowired
    private VoteRepo repository;

    @Test
    public void create() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RestaurantTestData.REST1)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created = TestUtil.readFromJson(actions, Vote.class);
        newVote.setId(created.getId());
        voteMatcher.assertMatch(created, newVote);
        voteMatcher.assertMatch(repository.findById(newVote.getId()).get(), newVote);
    }

    @Test
    public void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        if (LocalTime.now().isBefore(VoteController.LAST_TIME_TO_CHANGE_VOTE)) {
            perform(updated);
            voteMatcher.assertMatch(repository.findById(updated.getId()).get(), updated);
        } else {
            Throwable throwable = Assertions.catchThrowable(() -> perform(updated));
            throwable = ValidationUtil.getRootCause(throwable);
            Assertions.assertThat(throwable).isInstanceOf(CannotChangeVoteException.class);
        }
    }

    public void perform(Vote updated) throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(REST_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated.getRestaurant())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getVotes() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(toMatcher.contentJson(List.of(new VoteTo(VOTE1))));
    }

    @Test
    public void getVotesToday() throws Exception {
        Vote vote1 = repository.save(new Vote(null, UserTestData.USER1, RestaurantTestData.REST1));
        Vote vote2 = repository.save(new Vote(null, UserTestData.USER2, RestaurantTestData.REST1));
        mvc.perform(MockMvcRequestBuilders.get(REST_URL + "/result"))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(toMatcher.contentJson(List.of(new VoteTo(vote1), new VoteTo(vote2))));
    }


}