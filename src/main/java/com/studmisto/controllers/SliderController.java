package com.studmisto.controllers;

import com.studmisto.entities.SliderItem;
import com.studmisto.repos.SliderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slider")
public class SliderController {
    @Autowired
    private SliderRepo sliderRepo;

    @GetMapping
    public List<SliderItem> getAllSliderItems() {
        return sliderRepo.findAll();
    }

    @GetMapping("{id}")
    public SliderItem getSliderItem(@PathVariable("id") SliderItem sliderItem) {
        return sliderItem;
    }

    @PostMapping
    public List<SliderItem> add(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("photoLink") String photoLink) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setTitle(title);
        sliderItem.setDescription(description);
        sliderItem.setPhotoLink(photoLink);
        sliderRepo.save(sliderItem);
        return sliderRepo.findAll();
    }

    @PutMapping("{id}")
    public List<SliderItem> update(@PathVariable("id") SliderItem sliderItemOld,
                                   @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("photoLink") String photoLink) {
        sliderItemOld.setTitle(title);
        sliderItemOld.setDescription(description);
        sliderItemOld.setPhotoLink(photoLink);
        sliderRepo.save(sliderItemOld);
        return sliderRepo.findAll();
    }

    @DeleteMapping("{id}")
    public List<SliderItem> delete(@PathVariable("id") SliderItem sliderItem) {
        sliderRepo.delete(sliderItem);
        return sliderRepo.findAll();
    }
}
