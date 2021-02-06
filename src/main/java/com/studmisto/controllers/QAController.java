package com.studmisto.controllers;

import com.studmisto.entities.QAItem;
import com.studmisto.repositories.QARepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
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
    public Map<String, Object> add(@RequestParam("question") String question,
                                   @RequestParam("answer") String answer) {
        QAItem qaItem = new QAItem();
        qaItem.setQuestion(question);
        qaItem.setAnswer(answer);
        qaRepository.save(qaItem);
        return Map.of("message", "Питання додано");
    }

    @PutMapping("{id}")
    public Map<String, Object> update(@PathVariable("id") QAItem qaItemOld,
                                      @RequestParam("question") String question,
                                      @RequestParam("answer") String answer) {
        try {
            qaItemOld.setQuestion(question);
            qaItemOld.setAnswer(answer);
            qaRepository.save(qaItemOld);
            return Map.of("message", "Питання додано");
        } catch(NullPointerException e) {
            return Map.of("errorMessage", "Питання з таким id не знайдено");
        }
    }

    @DeleteMapping("{id}")
    public Map<String, Object> delete(@PathVariable("id") QAItem qaItem) {
        try {
            qaRepository.delete(qaItem);
            return Map.of("message", "Питання видалено");
        } catch (InvalidDataAccessApiUsageException e) {
            return Map.of("errorMessage", "Питання з таким id не знайдено");
        }
    }
}