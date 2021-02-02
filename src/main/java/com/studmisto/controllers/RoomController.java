package com.studmisto.controllers;

import com.studmisto.entities.Room;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.Dorm;
import com.studmisto.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms(@RequestParam(name = "dorm", required = false) String dorm) {
        if(dorm == null) {
            return roomService.getAllRooms();
        } else {
            Dorm dorm1 = Dorm.valueOf(dorm);
            return roomService.getAllRooms(dorm1);
        }
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") Room room) {
        return room;
    }

    @GetMapping("/available")
    public List<Room> getAvailable(@AuthenticationPrincipal User user, @RequestParam(name = "dorm", required = false) String dorm) {
        if(dorm != null) {
            return roomService.getAvailableRoomsForUser(user, Dorm.valueOf(dorm));
        } else {
            return roomService.getAvailableRoomsForUser(user);
        }
    }

    @PostMapping
    public ResponseEntity addRoom(@RequestParam("roomNumber") Integer roomNumber,
                                  @RequestParam("dorm") String dormName,
                                  @RequestParam("stage") Integer stage,
                                  @RequestParam("places") Integer places) {
        Dorm dorm = Dorm.valueOf(dormName);
        if(roomService.checkUnique(roomNumber, dorm)) {
            Room room = new Room();
            room.setDorm(dorm);
            room.setStage(stage);
            room.setRoomNumber(roomNumber);
            room.setPlaces(places);
            room.setAvailablePlaces(places);
            room.setGender(null);
            roomService.addRoom(room);
            return ResponseEntity.ok("Кімната додана");
        } else {
            return new ResponseEntity<>("Кімната вже існує", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public void changeRoom(@PathVariable("id") Room room,
                           @RequestParam("places") Integer places) {
        room.setPlaces(places);
        roomService.addRoom(room);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") Room room) {
        if(roomService.delete(room)) {
            return ResponseEntity.ok("Кімната видалена");
        } else {
            return new ResponseEntity<>("Кімната не може бути видалена, поки в ній проживають", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
