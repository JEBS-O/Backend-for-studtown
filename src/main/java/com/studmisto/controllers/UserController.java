package com.studmisto.controllers;

import com.studmisto.entities.*;
import com.studmisto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/principal")
    public User getPrincipalUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") User user) {
        return user;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    public User addUser(@RequestParam("firstName") String firstName,
                        @RequestParam("secondName") String secondName,
                        @RequestParam("email") String email,
                        @RequestParam("gender") String gender,
                        @RequestParam("groupName") String groupName,
                        @RequestParam("institute") String institute,
                        @RequestParam(name = "room", required = false, defaultValue = "1") Integer room,
                        @RequestParam(name = "balance", required = false, defaultValue = "1.0") Double balance,
                        @RequestParam(name = "tariff", required = false, defaultValue = "STANDART") String tariff,
                        @RequestParam(name = "role", required = false, defaultValue = "USER") String role) {
        Gender gender1 = Gender.valueOf(gender);
        Institute institute1 = Institute.valueOf(institute);
        Tariff tariff1 = Tariff.valueOf(tariff);
        Role role1 = Role.valueOf(role);
        User user = new User();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setEmail(email);
        user.setGender(gender1);
        user.setGroupName(groupName);
        user.setInstitute(institute1);
        user.setRoom(room);
        user.setBalance(balance);
        user.setTariff(tariff1);
        user.setRole(role1);
        return userService.save(user);
    }

    @PutMapping("{id}")
    public void updateUser() {

    }

    @DeleteMapping("{id}")
    public void deleteUser() {

    }
}