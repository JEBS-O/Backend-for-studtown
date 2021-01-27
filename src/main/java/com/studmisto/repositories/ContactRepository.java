package com.studmisto.repositories;

import com.studmisto.entities.ContactItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactItem, Long> {
}
