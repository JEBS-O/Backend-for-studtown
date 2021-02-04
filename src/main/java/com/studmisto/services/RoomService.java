package com.studmisto.services;

import com.studmisto.entities.Room;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.Dorm;
import com.studmisto.entities.enums.Gender;
import com.studmisto.repositories.RoomRepository;
import com.studmisto.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getInformationAboutRoom(Room room) {
        Map<String, Object> answer = new HashMap<>();
        answer.put("id", room.getId());
        answer.put("dorm", room.getDorm().getAddress());
        answer.put("stage", room.getStage());
        answer.put("roomNumber", room.getRoomNumber());
        answer.put("availablePlaces", room.getAvailablePlaces());
        answer.put("places", room.getPlaces());
        answer.put("gender", room.getGender().getName());
        return answer;
    }

    public Room getRoom(Integer roomNumber, Dorm dorm) {
        Room room = roomRepository.findRoomByRoomNumberAndDorm(roomNumber, dorm);
        if(room == null) throw new NullPointerException("Кімнати №" + roomNumber + " у " + dorm.getAddress() + " не знайдено");
        else return room;
    }

    public boolean checkRoomForUser(Gender gender, Room room) {
        if(room == null || (room.getGender() != null && room.getGender() != gender) || room.getAvailablePlaces() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Room> getAvailableRoomsForUser(User user, Dorm dorm) {
        List<Room> rooms = roomRepository.findRoomsByGenderAndDormAndAvailablePlacesGreaterThan(user.getGender(), dorm, 0);
        List<Room> freeRooms = roomRepository.findRoomsByGenderAndDormAndAvailablePlacesGreaterThan(null, dorm, 0);
        rooms.addAll(freeRooms);
        return rooms;
    }

    public List<Room> getAvailableRoomsForUser(User user) {
        List<Room> rooms = roomRepository.findRoomsByGenderAndAvailablePlacesGreaterThan(user.getGender(), 0);
        List<Room> freeRooms = roomRepository.findRoomsByGenderAndAvailablePlacesGreaterThan(null, 0);
        rooms.addAll(freeRooms);
        return rooms;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAllRooms(Dorm dorm) {
        return roomRepository.findRoomsByDorm(dorm);
    }

    public Room giveRoomForUser(Gender gender, Room room) throws NullPointerException, IllegalArgumentException {
        if (room.getGender() == null) {
            room.setGender(gender);
        }
        room.setAvailablePlaces(room.getAvailablePlaces() - 1);
        roomRepository.save(room);
        return room;
    }

    public void takeRoomFromUser(User user, Room room) {
        room.setAvailablePlaces(room.getAvailablePlaces()+1);
        if(room.getAvailablePlaces().intValue() == room.getPlaces().intValue()) {
            room.setGender(null);
        }
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public boolean checkUnique(Integer roomNumber, Dorm dorm) {
        Room room = roomRepository.findRoomByRoomNumberAndDorm(roomNumber, dorm);
        return room == null;
    }

    public boolean delete(Room room) {
        List<User> usersInRoom = userRepository.findByRoom(room);
        if(usersInRoom == null) {
            roomRepository.delete(room);
            return true;
        } else {
            return false;
        }
    }
}
