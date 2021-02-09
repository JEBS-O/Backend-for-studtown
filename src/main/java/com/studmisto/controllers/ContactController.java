package com.studmisto.controllers;

import com.studmisto.entities.ContactItem;
import com.studmisto.entities.enums.ContactCategory;
import com.studmisto.repositories.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
            log.info("Додано елемент ContactItem з іменем {}", contactItem.getName());
            return Map.of("message", "Контакт додано");
        } catch(IllegalArgumentException e) {
            log.info("{} при додаванні елементу ContactItem", e.getMessage());
            return Map.of("errorMessage", e.getMessage());
        } catch(TransactionSystemException e) {
            log.error("При збереженні нового елементу ContactItem сталася помилка. {}", e.getMessage());
            return Map.of("errorMessage", "Неможливо зберегти контакт. Перевірте правильність введених полів. Можливо, проблема в полі email.");
        }
    }

    @PutMapping("{id}")
    public Map<String, Object> update(@PathVariable("id") ContactItem contactItem,
                                      @RequestParam(name = "address") String address,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "phoneNumber1") String phoneNumber1,
                                      @RequestParam(name = "phoneNumber2") String phoneNumber2) {
        try {
            contactItem.setAddress(address);
            contactItem.setEmail(email);
            contactItem.setPhoneNumber1(phoneNumber1);
            contactItem.setPhoneNumber2(phoneNumber2);
            contactRepository.save(contactItem);
            log.info("Змінено елемент ContactItem з id {}", contactItem.getId());
            return Map.of("message", "Контакт змінено");
        } catch(NullPointerException e) {
            log.error("NPE при спробі змінити ContactItem з id {}", contactItem.getId());
            return Map.of("errorMessage", "Контакт з таким id не знайдено");
        } catch(TransactionSystemException e) {
            log.error("При спробі зберегти зміни у елементі ContactItem з id {} сталася помилка. {}", contactItem.getId(), e.getMessage());
            return Map.of("errorMessage", "Неможливо зберегти контакт. Перевірте правильність введених полів. Можливо, проблема в полі email.");
        }
    }

    @DeleteMapping("{id}")
    public Map<String, Object> delete(@PathVariable("id") ContactItem contactItem) {
        try {
            contactRepository.delete(contactItem);
            log.info("Видалено елемент ContactItem з id {}", contactItem.getId());
            return Map.of("message", "Контакт видалено");
        } catch(InvalidDataAccessApiUsageException e) {
            log.error("NPE при спробі видалити ContactItem з id {}", contactItem.getId());
            return Map.of("errorMessage", "Контакт з таким id не знайдено");
        }
    }
}