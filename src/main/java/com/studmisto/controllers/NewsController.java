package com.studmisto.controllers;

import com.studmisto.entities.NewsItem;
import com.studmisto.repositories.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/news")
public class NewsController {
    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping
    public List<NewsItem> getAllNewsItems() {
        return newsRepository.findAll();
    }

    @GetMapping("{id}")
    public NewsItem getNewsItem(@PathVariable("id") NewsItem newsItem) {
        return newsItem;
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> add(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("photoLink") String photoLink) {
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);
        newsItem.setDescription(description);
        newsItem.setPhotoLink(photoLink);
        newsRepository.save(newsItem);
        log.info("Доданий елемент NewsItem з заголовком {}", newsItem.getTitle());
        return Map.of("message", "Новину додано");
    }

    @PutMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> update(@PathVariable("id") NewsItem newsItem,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      @RequestParam("photoLink") String photoLink) {
        try {
            newsItem.setTitle(title);
            newsItem.setDescription(description);
            newsItem.setPhotoLink(photoLink);
            newsRepository.save(newsItem);
            log.info("Змінено елемент NewsItem з id {}", newsItem.getId());
            return Map.of("message", "Новину змінено");
        } catch(NullPointerException e) {
            log.error("NPE при спробі змінити NewsItem з id {}", newsItem.getId());
            return Map.of("errorMessage", "Новину з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> delete(@PathVariable("id") NewsItem newsItem) {
        try {
            newsRepository.delete(newsItem);
            log.info("Видалено елемент NewsItem з id {}", newsItem.getId());
            return Map.of("message", "Новину видалено");
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("NPE при спробі видалити NewsItem з id {}", newsItem.getId());
            return Map.of("errorMessage", "Новину з таким id не знайдено");
        }
    }
}