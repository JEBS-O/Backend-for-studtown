package com.studmisto.controllers;

import com.studmisto.entities.User;
import com.studmisto.entities.enums.*;
import com.studmisto.services.RoomService;
import com.studmisto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

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

    @PostMapping("/addStaff")
    public Map<Object, Object> addStaff(@RequestParam("firstName") String firstName,
                                  @RequestParam("secondName") String secondName,
                                  @RequestParam("email") String email,
                                  @RequestParam("gender") String gender,
                                  @RequestParam("position") String position,
                                  @RequestParam("status") String status,
                                  @RequestParam("password") String password) {
        Map<Object, Object> answer = new HashMap<>();
        answer.put("firstName", firstName);
        answer.put("secondName", secondName);
        answer.put("email", email);
        answer.put("gender", gender);
        answer.put("position", position);
        answer.put("status", status);
        try {
            User user = new User();
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setEmail(email);
            user.setPosition(position);
            user.setGender(Gender.getGender(gender));
            user.setRole(Role.ADMIN);
            user.setStatus(Status.getStatus(status));
            user.setPassword(new BCryptPasswordEncoder(12).encode(password));
            userService.save(user);
            answer.put("message", "Користувач доданий");
        } catch(IllegalArgumentException e) {
            answer.put("errorMessage", e.getMessage());
        }
        return answer;
    }

    @PostMapping("/addExistInmate")
    public Map<Object, Object> addExistInmate(@RequestParam("firstName") String firstName,
                                  @RequestParam("secondName") String secondName,
                                  @RequestParam("email") String email,
                                  @RequestParam("gender") String gender,
                                  @RequestParam("groupName") String groupName,
                                  @RequestParam("institute") String institute,
                                  @RequestParam("roomNumber") Integer roomNumber,
                                  @RequestParam("dorm") String dorm,
                                  @RequestParam("password") String password,
                                  @RequestParam("position") String position,
                                  @RequestParam("status") String status,
                                  @RequestParam("balance") Double balance,
                                  @RequestParam("tariff") String tariff) {
        Map<Object, Object> answer = new HashMap<>();
        answer.put("firstName", firstName);
        answer.put("secondName", secondName);
        answer.put("email", email);
        answer.put("gender", gender);
        answer.put("groupName", groupName);
        answer.put("institute", institute);
        answer.put("roomNumber", roomNumber);
        answer.put("dorm", dorm);
        answer.put("position", position);
        answer.put("status", status);
        answer.put("balance", balance);
        answer.put("tariff", tariff);

        User user = new User();
        try {
            if(roomService.checkRoomForUser(Gender.getGender(gender), roomService.getRoom(roomNumber, Dorm.getDorm(dorm)))) {
                user.setFirstName(firstName);
                user.setSecondName(secondName);
                user.setEmail(email);
                user.setGender(Gender.getGender(gender));
                user.setGroupName(groupName);
                user.setInstitute(Institute.getInstitute(institute));
                user.setStatus(Status.getStatus(status));
                user.setPosition(position);
                user.setRoom(roomService.giveRoomForUser(Gender.getGender(gender), roomService.getRoom(roomNumber, Dorm.getDorm(dorm))));
                user.setBalance(balance);
                user.setTariff(Tariff.getTariff(tariff));
                user.setRole(Role.USER);
                user.setPassword(new BCryptPasswordEncoder(12).encode(password));
                userService.save(user);
                answer.put("message", "Користувач доданий");
            } else {
                answer.put("errorMessage", "Користувач не може бути заселений у дану кімнату");
            }
        } catch(NullPointerException | IllegalArgumentException e) {
            answer.put("errorMessage", e.getMessage());
        } finally {
            if(user.getRoom() != null) {
                roomService.takeRoomFromUser(user, user.getRoom());
            }
        }
        return answer;
    }

    @PutMapping("{id}")
    public void updateUser() {

    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") User user) {
        roomService.takeRoomFromUser(user, user.getRoom());
        userService.delete(user);
    }
}