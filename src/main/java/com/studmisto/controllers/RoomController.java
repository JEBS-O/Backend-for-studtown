package com.studmisto.controllers;

import com.studmisto.entities.Room;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.Dorm;
import com.studmisto.services.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllRooms(@RequestParam(name = "dorm", required = false) String dorm) {
        Dorm dorm1 = null;
        if (dorm != null) {
            try {
                dorm1 = Dorm.getDorm(dorm);
            } catch (IllegalArgumentException e) {
                dorm = null;
            }
        }
        List<Map<String, Object>> answer = new ArrayList<>();
        if (dorm == null) {
            for (Room room : roomService.getAllRooms()) {
                answer.add(roomService.getInformationAboutRoom(room));
            }
        } else {
            for (Room room : roomService.getAllRooms(dorm1)) {
                answer.add(roomService.getInformationAboutRoom(room));
            }
        }
        return answer;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getRoom(@PathVariable("id") Room room) {
        return roomService.getInformationAboutRoom(room);
    }

    @GetMapping("/available")
    public List<Map<String, Object>> getAvailable(@AuthenticationPrincipal User user, @RequestParam(name = "dorm", required = false) String dorm) {
        Dorm dorm1 = null;
        if (dorm != null) {
            try {
                dorm1 = Dorm.getDorm(dorm);
            } catch (IllegalArgumentException e) {
                dorm = null;
            }
        }
        List<Map<String, Object>> answer = new ArrayList<>();
        if (dorm != null) {
            for (Room room : roomService.getAvailableRoomsForUser(user, dorm1)) {
                answer.add(roomService.getInformationAboutRoom(room));
            }
        } else {
            for (Room room : roomService.getAvailableRoomsForUser(user)) {
                answer.add(roomService.getInformationAboutRoom(room));
            }
        }
        return answer;
    }

    @PostMapping
    public Map<String, Object> addRoom(@RequestParam("roomNumber") Integer roomNumber,
                                       @RequestParam("dorm") String dormName,
                                       @RequestParam("stage") Integer stage,
                                       @RequestParam("places") Integer places) {
        try {
            if (roomService.checkUnique(roomNumber, Dorm.getDorm(dormName))) {
                Room room = new Room();
                room.setDorm(Dorm.getDorm(dormName));
                room.setStage(stage);
                room.setRoomNumber(roomNumber);
                room.setPlaces(places);
                room.setAvailablePlaces(places);
                room.setGender(null);
                roomService.addRoom(room);
                log.info("Додана кімната №{} у {}", room.getRoomNumber(), room.getDorm().getAddress());
                return Map.of("message", "Кімната додана");
            } else {
                log.warn("Спроба додати повторно кімнату №{} у {}", roomNumber, dormName);
                return Map.of("errorMessage", "Кімната вже існує");
            }
        } catch (IllegalArgumentException e) {
            log.error("{} при спробі додати кімнату №{} у {}", e.getMessage(), roomNumber, dormName);
            return Map.of("errorMessage", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> changeRoom(@PathVariable("id") Room room,
                                          @RequestParam("places") Integer places) {
        try {
            Integer changer = places - room.getPlaces();
            room.setPlaces(room.getPlaces() + changer);
            room.setAvailablePlaces(room.getAvailablePlaces() + changer);
            roomService.addRoom(room);
            log.info("Кількість місць у кімнаті №{} у {} змінено на {}", room.getRoomNumber(), room.getDorm().getAddress(), changer);
            return Map.of("message", "Кількість місць змінено");
        } catch (NullPointerException e) {
            log.error("NPE при спробі змінити кількість місць у кімнаті");
            return Map.of("errorMessage", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteRoom(@PathVariable("id") Room room) {
        try {
            if (roomService.delete(room)) {
                log.info("Видалено кімнату №{} у {}", room.getRoomNumber(), room.getDorm().getAddress());
                return Map.of("message", "Кімната видалена");
            } else {
                log.warn("Невдала спроба видалити кімнату №{} у {}, в якій проживають", room.getRoomNumber(), room.getDorm().getAddress());
                return Map.of("errorMessage", "Кімната не може бути видалена, поки в ній проживають");
            }
        } catch (NullPointerException e) {
            log.error("NPE при спробі видалити кімнату");
            return Map.of("errorMessage", "Кімната з таким id не знайдена");
        }
    }
}