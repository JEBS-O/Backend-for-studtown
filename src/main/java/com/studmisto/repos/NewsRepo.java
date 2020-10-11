package com.studmisto.repos;

import com.studmisto.entities.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepo extends JpaRepository<NewsItem, Long> {
}
