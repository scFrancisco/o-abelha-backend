package com.example.oabelha.Controller;

import com.example.oabelha.Entity.Contact;
import com.example.oabelha.Repository.ContactRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactRepository repo;

    public ContactController(ContactRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        return repo.save(contact);
    }

    @GetMapping
    public List<Contact> findAll() {
        return repo.findAll();
    }
}
