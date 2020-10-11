package com.studmisto.repos;

import com.studmisto.entities.SliderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SliderRepo extends JpaRepository<SliderItem, Long> {
}
