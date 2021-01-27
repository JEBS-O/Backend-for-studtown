package com.studmisto.repositories;

import com.studmisto.entities.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsItem, Long> {
}
