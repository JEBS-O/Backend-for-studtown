package com.studmisto.repos;

import com.studmisto.entities.AboutItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepo extends JpaRepository<AboutItem, Long> {

}
