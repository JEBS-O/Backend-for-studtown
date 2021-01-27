package com.studmisto.controllers;

import com.studmisto.entities.ContactItem;
import com.studmisto.repositories.ContactRepository;
import com.studmisto.entities.ContactCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<ContactItem> getAllContactItems() {
        return contactRepository.findAll();
    }

    @GetMapping("{id}")
    public ContactItem getContactItem(@PathVariable("id") ContactItem contactItem) {
        return contactItem;
    }

    @PostMapping
    public List<ContactItem> add(@RequestParam(name = "category") String categoryName,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phoneNumber1") String phoneNumber1,
            @RequestParam(name = "phoneNumber2") String phoneNumber2)
    {
        ContactCategory category = ContactCategory.valueOf(categoryName);
        ContactItem contactItem = new ContactItem(category, name, position, address, email, phoneNumber1, phoneNumber2);
        contactRepository.save(contactItem);
        return contactRepository.findAll();
    }

    @PutMapping("{id}")
    public List<ContactItem> update(
            @PathVariable("id") ContactItem contactItemOld,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phoneNumber1") String phoneNumber1,
            @RequestParam(name = "phoneNumber2") String phoneNumber2)
        {
        contactItemOld.setAddress(address);
        contactItemOld.setEmail(email);
        contactItemOld.setPhoneNumber1(phoneNumber1);
        contactItemOld.setPhoneNumber2(phoneNumber2);
        contactRepository.save(contactItemOld);
        return contactRepository.findAll();
    }

    @DeleteMapping("{id}")
    public List<ContactItem> delete(@PathVariable("id") ContactItem contactItem) {
        contactRepository.delete(contactItem);
        return contactRepository.findAll();
    }

}
