package com.studmisto.controllers;

import com.studmisto.entities.NewsItem;
import com.studmisto.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRepository newsRepository;

    @GetMapping
    public List<NewsItem> getAllNewsItems() {
        return newsRepository.findAll();
    }

    @GetMapping("{id}")
    public NewsItem getNewsItem(@PathVariable("id") NewsItem newsItem) {
        return newsItem;
    }

    @PostMapping
    public List<NewsItem> add(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("photoLink") String photoLink) {
        NewsItem newsItem = new NewsItem(title, description, photoLink, new Date());
        newsRepository.save(newsItem);
        return newsRepository.findAll();
    }

    @PutMapping("{id}")
    public List<NewsItem> update(@PathVariable("id") NewsItem newsItemOld,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("photoLink") String photoLink) {
        newsItemOld.setTitle(title);
        newsItemOld.setDescription(description);
        newsItemOld.setPhotoLink(photoLink);
        newsRepository.save(newsItemOld);
        return newsRepository.findAll();
    }

    @DeleteMapping("{id}")
    public List<NewsItem> delete(@PathVariable("id") NewsItem newsItem) {
        newsRepository.delete(newsItem);
        return newsRepository.findAll();
    }
}
