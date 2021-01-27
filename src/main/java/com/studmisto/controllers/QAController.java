package com.studmisto.controllers;

import com.studmisto.entities.QAItem;
import com.studmisto.repositories.QARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qa")
public class QAController {
    @Autowired
    private QARepository qaRepository;

    @GetMapping
    public List<QAItem> getAllQAItems() {
        return qaRepository.findAll();
    }

    @GetMapping("{id}")
    public QAItem getQAItem(@PathVariable("id") QAItem qaItem) {
        return qaItem;
    }

    @PostMapping
    public List<QAItem> add(@RequestParam("question") String question,
                            @RequestParam("answer") String answer) {
        QAItem qaItem = new QAItem();
        qaItem.setQuestion(question);
        qaItem.setAnswer(answer);
        qaRepository.save(qaItem);
        return qaRepository.findAll();
    }

    @PutMapping("{id}")
    public List<QAItem> update(@PathVariable("id") QAItem qaItemOld,
                               @RequestParam("question") String question,
                               @RequestParam("answer") String answer) {
        qaItemOld.setQuestion(question);
        qaItemOld.setAnswer(answer);
        qaRepository.save(qaItemOld);
        return qaRepository.findAll();
    }

    @DeleteMapping("{id}")
    public List<QAItem> delete(@PathVariable("id") QAItem qaItem) {
        qaRepository.delete(qaItem);
        return qaRepository.findAll();
    }
}
