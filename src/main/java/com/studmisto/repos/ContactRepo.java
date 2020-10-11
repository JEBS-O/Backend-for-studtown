package com.studmisto.repos;

import com.studmisto.entities.ContactItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<ContactItem, Long> {
}
