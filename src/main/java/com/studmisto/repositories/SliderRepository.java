package com.studmisto.repositories;

import com.studmisto.entities.SliderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends JpaRepository<SliderItem, Long> {
}
