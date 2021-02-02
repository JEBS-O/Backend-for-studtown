package com.studmisto.repositories;

import com.studmisto.entities.Room;
import com.studmisto.entities.enums.Dorm;
import com.studmisto.entities.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findRoomByRoomNumberAndDorm(Integer number, Dorm dorm);
    List<Room> findRoomsByGenderAndDormAndAvailablePlacesGreaterThan(Gender gender, Dorm dorm, int placesGreaterThan);
    List<Room> findRoomsByGenderAndAvailablePlacesGreaterThan(Gender gender, int placesGreaterThen);
    List<Room> findRoomsByDorm(Dorm dorm);
}
