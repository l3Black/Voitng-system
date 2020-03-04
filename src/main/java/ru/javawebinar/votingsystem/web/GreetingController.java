package ru.javawebinar.votingsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.votingsystem.model.User;
import ru.javawebinar.votingsystem.repository.UserRepository;

import java.util.Map;

@Controller
public class GreetingController {
    private UserRepository repository;

    public GreetingController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<User> repositoryAll = repository.findAll();
        model.put("users", repositoryAll);
        return "main";
    }

    @PostMapping
    public String createUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, Map<String, Object> model) {
        User user = new User(name, email, password);
        repository.save(user);
        Iterable<User> repositoryAll = repository.findAll();
        model.put("users", repositoryAll);
        return "main";
    }

}