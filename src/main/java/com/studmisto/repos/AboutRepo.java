package com.studmisto.repos;

import com.studmisto.entities.AboutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AboutRepo extends JpaRepository<AboutItem, Long> {

}
