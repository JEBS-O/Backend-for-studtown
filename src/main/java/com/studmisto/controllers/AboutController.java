package com.studmisto.controllers;

import com.studmisto.entities.AboutItem;
import com.studmisto.repositories.AboutRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/about")
public class AboutController {
    private final AboutRepository aboutRepository;

    public AboutController(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    @GetMapping
    public List<AboutItem> getAllAboutItems() {
        return aboutRepository.findAll();
    }

    @GetMapping("{id}")
    public AboutItem getAboutItem(@PathVariable("id") AboutItem aboutItem) {
        return aboutItem;
    }

    @PostMapping
    public Map<String, Object> add(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("iconLink") String iconLink) {
        AboutItem aboutItem = new AboutItem();
        aboutItem.setTitle(title);
        aboutItem.setDescription(description);
        aboutItem.setIconLink(iconLink);
        aboutRepository.save(aboutItem);
        return Map.of("message", "Елемент успішно додано");
    }

    @PutMapping("{id}")
    public Map<String, Object> update(@PathVariable("id") AboutItem aboutItemOld,
                                  @RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("iconLink") String iconLink) {
        try {
            aboutItemOld.setTitle(title);
            aboutItemOld.setDescription(description);
            aboutItemOld.setIconLink(iconLink);
            aboutRepository.save(aboutItemOld);
            return Map.of("message", "Елемент успішно змінено");
        } catch(NullPointerException e) {
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    public Map<String, Object> delete(@PathVariable("id") AboutItem aboutItem) {
        try {
            aboutRepository.delete(aboutItem);
            return Map.of("message", "Елемент видалено");
        } catch(InvalidDataAccessApiUsageException e) {
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }
}