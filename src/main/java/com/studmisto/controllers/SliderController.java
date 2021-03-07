package com.studmisto.controllers;

import com.studmisto.entities.SliderItem;
import com.studmisto.repositories.SliderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> add(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("photoLink") String photoLink) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setTitle(title);
        sliderItem.setDescription(description);
        sliderItem.setPhotoLink(photoLink);
        sliderRepository.save(sliderItem);
        log.info("Доданий елемент SliderItem з заголовком {}", sliderItem.getTitle());
        return Map.of("message", "Елемент додано");
    }

    @PutMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> update(@PathVariable("id") SliderItem sliderItem,
                                   @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("photoLink") String photoLink) {
        try {
            sliderItem.setTitle(title);
            sliderItem.setDescription(description);
            sliderItem.setPhotoLink(photoLink);
            sliderRepository.save(sliderItem);
            log.info("Змінено елемент SliderItem з id {}", sliderItem.getId());
            return Map.of("message", "Елемент додано");
        } catch(NullPointerException e) {
            log.error("NPE при спробі змінити SliderItem з id {}", sliderItem.getId());
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> delete(@PathVariable("id") SliderItem sliderItem) {
        try {
            sliderRepository.delete(sliderItem);
            log.info("Видалено елемент SliderItem з id {}", sliderItem.getId());
            return Map.of("message", "Елемент видалено");
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("NPE при спробі видалити SliderItem з id {}", sliderItem.getId());
            return Map.of("errorMessage", "Елемент з таким id не знайдено");
        }
    }
}