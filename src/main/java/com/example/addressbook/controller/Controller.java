package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    ContactService contactService;

    @PostMapping("create")
    public ResponseEntity<List<Contact>> createContacts(@RequestBody List<Contact> contactList){
        return ResponseEntity.ok(contactService.createContacts(contactList));
    }

    @PutMapping("update")
    public ResponseEntity<List<Contact>> updateContact(@RequestBody List<Contact> contactList){
        return ResponseEntity.ok(contactService.updateContacts(contactList));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Map<String, Integer>> delete(@RequestBody List<String> ids) {
        int count = contactService.deleteContacts(ids);
        return ResponseEntity.ok(Collections.singletonMap("deleted", count));
    }

    @GetMapping("search")
    public ResponseEntity<List<Contact>> search(@RequestBody Map<String, String> body) {
        String query = body.getOrDefault("query", "");
        return ResponseEntity.ok(contactService.searchContacts(query));
    }



}
