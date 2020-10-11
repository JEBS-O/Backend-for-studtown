package com.studmisto.controllers;

import com.studmisto.entities.NewsItem;
import com.studmisto.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRepo newsRepo;

    @GetMapping
    public List<NewsItem> getAllNewsItems() {
        return newsRepo.findAll();
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
        newsRepo.save(newsItem);
        return newsRepo.findAll();
    }

    @PutMapping("{id}")
    public List<NewsItem> update(@PathVariable("id") NewsItem newsItemOld,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("photoLink") String photoLink) {
        newsItemOld.setTitle(title);
        newsItemOld.setDescription(description);
        newsItemOld.setPhotoLink(photoLink);
        newsRepo.save(newsItemOld);
        return newsRepo.findAll();
    }

    @DeleteMapping("{id}")
    public List<NewsItem> delete(@PathVariable("id") NewsItem newsItem) {
        newsRepo.delete(newsItem);
        return newsRepo.findAll();
    }
}
