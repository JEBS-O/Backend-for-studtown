package com.studmisto.repositories;

import com.studmisto.entities.AboutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutRepository extends JpaRepository<AboutItem, Long> {

}
