package com.studmisto.controllers;

import com.studmisto.entities.AboutItem;
import com.studmisto.repositories.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {
    @Autowired
    private AboutRepository aboutRepository;

    @GetMapping
    public List<AboutItem> getAllAboutItems() {
        return aboutRepository.findAll();
    }

    @GetMapping("{id}")
    public AboutItem getAboutItem(@PathVariable("id") AboutItem aboutItem) {
        return aboutItem;
    }

    @PostMapping
    public List<AboutItem> add(@RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("iconLink") String iconLink) {
        AboutItem aboutItem = new AboutItem(title, description, iconLink);
        aboutRepository.save(aboutItem);
        return aboutRepository.findAll();
    }

    @PutMapping("{id}")
    public List<AboutItem> update(@PathVariable("id") AboutItem aboutItemOld,
                                  @RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("iconLink") String iconLink) {
        aboutItemOld.setTitle(title);
        aboutItemOld.setDescription(description);
        aboutItemOld.setIconLink(iconLink);
        aboutRepository.save(aboutItemOld);
        return aboutRepository.findAll();
    }

    @DeleteMapping("{id}")
    public List<AboutItem> delete(@PathVariable("id") AboutItem aboutItem) {
        aboutRepository.delete(aboutItem);
        return aboutRepository.findAll();
    }
}
