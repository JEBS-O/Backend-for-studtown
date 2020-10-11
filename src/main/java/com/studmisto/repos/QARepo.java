package com.studmisto.repos;

import com.studmisto.entities.QAItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QARepo extends JpaRepository<QAItem, Long> {
}
