package com.studmisto.controllers;

import com.studmisto.entities.QAItem;
import com.studmisto.repositories.QARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/qa")
public class QAController {
    private final QARepository qaRepository;

    public QAController(QARepository qaRepository) {
        this.qaRepository = qaRepository;
    }

    @GetMapping
    public List<QAItem> getAllQAItems() {
        return qaRepository.findAll();
    }

    @GetMapping("{id}")
    public QAItem getQAItem(@PathVariable("id") QAItem qaItem) {
        return qaItem;
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> add(@RequestParam("question") String question,
                                   @RequestParam("answer") String answer) {
        QAItem qaItem = new QAItem();
        qaItem.setQuestion(question);
        qaItem.setAnswer(answer);
        qaRepository.save(qaItem);
        log.info("Доданий елемент QAItem {}", qaItem.getQuestion());
        return Map.of("message", "Питання додано");
    }

    @PutMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> update(@PathVariable("id") QAItem qaItem,
                                      @RequestParam("question") String question,
                                      @RequestParam("answer") String answer) {
        try {
            qaItem.setQuestion(question);
            qaItem.setAnswer(answer);
            qaRepository.save(qaItem);
            log.info("Змінено елемент QAItem з id {}", qaItem.getId());
            return Map.of("message", "Питання додано");
        } catch(NullPointerException e) {
            log.error("NPE при спробі змінити QAItem з id {}", qaItem.getId());
            return Map.of("errorMessage", "Питання з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, Object> delete(@PathVariable("id") QAItem qaItem) {
        try {
            qaRepository.delete(qaItem);
            log.info("Видалено елемент QAItem з id {}", qaItem.getId());
            return Map.of("message", "Питання видалено");
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("NPE при спробі видалити QAItem з id {}", qaItem.getId());
            return Map.of("errorMessage", "Питання з таким id не знайдено");
        }
    }
}