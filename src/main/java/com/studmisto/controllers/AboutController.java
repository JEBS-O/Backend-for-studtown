package com.studmisto.controllers;

import com.studmisto.entities.AboutItem;
import com.studmisto.repos.AboutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {
    @Autowired
    private AboutRepo aboutRepo;

    @GetMapping
    public List<AboutItem> getAllAboutItems() {
        return aboutRepo.findAll();
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
        aboutRepo.save(aboutItem);
        return aboutRepo.findAll();
    }

    @PutMapping("{id}")
    public List<AboutItem> update(@PathVariable("id") AboutItem aboutItemOld,
                                  @RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("iconLink") String iconLink) {
        aboutItemOld.setTitle(title);
        aboutItemOld.setDescription(description);
        aboutItemOld.setIconLink(iconLink);
        aboutRepo.save(aboutItemOld);
        return aboutRepo.findAll();
    }

    @DeleteMapping("{id}")
    public List<AboutItem> delete(@PathVariable("id") AboutItem aboutItem) {
        aboutRepo.delete(aboutItem);
        return aboutRepo.findAll();
    }
}
