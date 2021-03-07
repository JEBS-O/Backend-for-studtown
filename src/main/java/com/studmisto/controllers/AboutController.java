package com.studmisto.controllers;

import com.studmisto.entities.AboutItem;
import com.studmisto.repositories.AboutRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> add(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("iconLink") String iconLink) {
        AboutItem aboutItem = new AboutItem();
        aboutItem.setTitle(title);
        aboutItem.setDescription(description);
        aboutItem.setIconLink(iconLink);
        aboutRepository.save(aboutItem);
        log.info("Доданий елемент AboutItem з заголовком {}", aboutItem.getTitle());
        return Map.of("message", "Елемент успішно додано");
    }

    @PutMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> update(@PathVariable("id") AboutItem aboutItem,
                                  @RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("iconLink") String iconLink) {
        try {
            aboutItem.setTitle(title);
            aboutItem.setDescription(description);
            aboutItem.setIconLink(iconLink);
            aboutRepository.save(aboutItem);
            log.info("Змінено елемент AboutItem з id {}", aboutItem.getId());
            return Map.of("message", "Елемент успішно змінено");
        } catch(NullPointerException e) {
            log.error("NPE при спробі змінити AboutItem з id {}", aboutItem.getId());
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> delete(@PathVariable("id") AboutItem aboutItem) {
        try {
            aboutRepository.delete(aboutItem);
            log.info("Видалено елемент AboutItem з id {}", aboutItem.getId());
            return Map.of("message", "Елемент видалено");
        } catch(InvalidDataAccessApiUsageException e) {
            log.error("NPE при спробі видалити AboutItem з id {}", aboutItem.getId());
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }
}