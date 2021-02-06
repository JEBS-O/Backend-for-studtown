package com.studmisto.controllers;

import com.studmisto.entities.NewsItem;
import com.studmisto.repositories.NewsRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
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
    public Map<String, Object> add(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("photoLink") String photoLink) {
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);
        newsItem.setDescription(description);
        newsItem.setPhotoLink(photoLink);
        newsRepository.save(newsItem);
        return Map.of("message", "Новину додано");
    }

    @PutMapping("{id}")
    public Map<String, Object> update(@PathVariable("id") NewsItem newsItemOld,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      @RequestParam("photoLink") String photoLink) {
        try {
            newsItemOld.setTitle(title);
            newsItemOld.setDescription(description);
            newsItemOld.setPhotoLink(photoLink);
            newsRepository.save(newsItemOld);
            return Map.of("message", "Новину змінено");
        } catch(NullPointerException e) {
            return Map.of("errorMessage", "Новину з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    public Map<String, Object> delete(@PathVariable("id") NewsItem newsItem) {
        try {
            newsRepository.delete(newsItem);
            return Map.of("message", "Новину видалено");
        } catch (InvalidDataAccessApiUsageException e) {
            return Map.of("errorMessage", "Новину з таким id не знайдено");
        }
    }
}