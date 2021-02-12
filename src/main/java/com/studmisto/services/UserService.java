package com.studmisto.services;

import com.studmisto.entities.User;
import com.studmisto.entities.enums.Status;
import com.studmisto.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CustomUserDetailsService")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s);
    }

    public List<User> getUsers(Status status) {
        return userRepository.findUsersByStatus(status);
    }

    public Map<String, Object> getBasicUserInfo(User user) {
        Status status = user.getStatus();
        if(status.equals(Status.INMATE) || status.equals(Status.WAITING_FOR_ORDER)) {
            return getBasicUserInfoForInmate(user);
        } else {
            return getBasicUserInfoForStaff(user);
        }
    }

    private Map<String, Object> getBasicUserInfoForInmate(User user) {
        Map<String, Object> answer = new HashMap<>();
        answer.put("name", user.getUsername());
        answer.put("email", user.getEmail());
        answer.put("gender", user.getGender().getName());
        answer.put("institute", user.getInstitute().getName());
        answer.put("groupName", user.getGroupName());
        answer.put("status", user.getStatus().getName());
        answer.put("position", user.getPosition());
        answer.put("dorm", user.getRoom().getDorm());
        answer.put("roomNumber", user.getRoom().getRoomNumber());
        answer.put("balance", user.getBalance());
        answer.put("tariff", user.getTariff().getDescription());
        answer.put("userpic", user.getUserpic());
        return answer;
    }

    private Map<String, Object> getBasicUserInfoForStaff(User user) {
        Map<String, Object> answer = new HashMap<>();
        answer.put("name", user.getUsername());
        answer.put("email", user.getEmail());
        answer.put("gender", user.getGender().getName());
        answer.put("status", user.getStatus().getName());
        answer.put("position", user.getPosition());
        answer.put("userpic", user.getUserpic());
        answer.put("institute", "");
        answer.put("groupName", "");
        answer.put("dorm", "");
        answer.put("room", "");
        answer.put("tariff", "");
        return answer;
    }

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> answer = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user : users) {
            Map<String, Object> map = getBasicUserInfo(user);
            map.put("id", user.getId());
            answer.add(map);
        }
        return answer;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        while(password.toString().length()<10) {
            int generatedCharacter = (char) (33 + (int) (93 * Math.random()));
            if ((generatedCharacter >= 48 && generatedCharacter <= 57) || (generatedCharacter >= 65 && generatedCharacter <= 90) || (generatedCharacter >= 97 && generatedCharacter <= 122)) {
                password.append((char) generatedCharacter);
            }
        }
        return password.toString();
    }

    public boolean checkEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }
}
