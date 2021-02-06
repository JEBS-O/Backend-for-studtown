package com.studmisto.controllers;

import com.studmisto.entities.SliderItem;
import com.studmisto.repositories.SliderRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/slider")
public class SliderController {
    private final SliderRepository sliderRepository;

    public SliderController(SliderRepository sliderRepository) {
        this.sliderRepository = sliderRepository;
    }

    @GetMapping
    public List<SliderItem> getAllSliderItems() {
        return sliderRepository.findAll();
    }

    @GetMapping("{id}")
    public SliderItem getSliderItem(@PathVariable("id") SliderItem sliderItem) {
        return sliderItem;
    }

    @PostMapping
    public Map<String, Object> add(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("photoLink") String photoLink) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setTitle(title);
        sliderItem.setDescription(description);
        sliderItem.setPhotoLink(photoLink);
        sliderRepository.save(sliderItem);
        return Map.of("message", "Елемент додано");
    }

    @PutMapping("{id}")
    public Map<String, Object> update(@PathVariable("id") SliderItem sliderItemOld,
                                   @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("photoLink") String photoLink) {
        try {
            sliderItemOld.setTitle(title);
            sliderItemOld.setDescription(description);
            sliderItemOld.setPhotoLink(photoLink);
            sliderRepository.save(sliderItemOld);
            return Map.of("message", "Елемент додано");
        } catch(NullPointerException e) {
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    public Map<String, Object> delete(@PathVariable("id") SliderItem sliderItem) {
        try {
            sliderRepository.delete(sliderItem);
            return Map.of("message", "Елемент видалено");
        } catch (InvalidDataAccessApiUsageException e) {
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }
}