package com.studmisto.repositories;

import com.studmisto.entities.Appeal;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
    List<Appeal> findAppealsByAuthor(User user);
    List<Appeal> findAppealsByRecipient(Status status);
}
