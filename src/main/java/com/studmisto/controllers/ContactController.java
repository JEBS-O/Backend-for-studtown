package com.studmisto.controllers;

import com.studmisto.entities.ContactItem;
import com.studmisto.entities.enums.ContactCategory;
import com.studmisto.repositories.ContactRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public List<ContactItem> getAllContactItems() {
        return contactRepository.findAll();
    }

    @GetMapping("{id}")
    public ContactItem getContactItem(@PathVariable("id") ContactItem contactItem) {
        return contactItem;
    }

    @PostMapping
    public Map<String, Object> add(@RequestParam(name = "category") String categoryName,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "position") String position,
                                   @RequestParam(name = "address", required = false) String address,
                                   @RequestParam(name = "email") String email,
                                   @RequestParam(name = "phoneNumber1") String phoneNumber1,
                                   @RequestParam(name = "phoneNumber2", required = false) String phoneNumber2) {
        try {
            ContactItem contactItem = new ContactItem();
            contactItem.setName(name);
            contactItem.setCategory(ContactCategory.getContactCategory(categoryName));
            contactItem.setPosition(position);
            contactItem.setAddress(address);
            contactItem.setEmail(email);
            contactItem.setPhoneNumber1(phoneNumber1);
            contactItem.setPhoneNumber2(phoneNumber2);
            contactRepository.save(contactItem);
            return Map.of("message", "Контакт додано");
        } catch(IllegalArgumentException e) {
            return Map.of("errorMessage", e.getMessage());
        } catch(TransactionSystemException e) {
            return Map.of("errorMessage", "Неможливо зберегти контакт. Перевірте правильність введених полів. Можливо, проблема в полі email.");
        }
    }

    @PutMapping("{id}")
    public Map<String, Object> update(@PathVariable("id") ContactItem contactItemOld,
                                      @RequestParam(name = "address") String address,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "phoneNumber1") String phoneNumber1,
                                      @RequestParam(name = "phoneNumber2") String phoneNumber2) {
        try {
            contactItemOld.setAddress(address);
            contactItemOld.setEmail(email);
            contactItemOld.setPhoneNumber1(phoneNumber1);
            contactItemOld.setPhoneNumber2(phoneNumber2);
            contactRepository.save(contactItemOld);
            return Map.of("message", "Контакт змінено");
        } catch(NullPointerException e) {
            return Map.of("errorMessage", "Контакт з таким id не знайдено");
        } catch(TransactionSystemException e) {
            return Map.of("errorMessage", "Неможливо зберегти контакт. Перевірте правильність введених полів. Можливо, проблема в полі email.");
        }
    }

    @DeleteMapping("{id}")
    public Map<String, Object> delete(@PathVariable("id") ContactItem contactItem) {
        try {
            contactRepository.delete(contactItem);
            return Map.of("message", "Контакт видалено");
        } catch(InvalidDataAccessApiUsageException e) {
            return Map.of("errorMessage", "Контакт з таким id не знайдено");
        }
    }

}