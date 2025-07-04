package com.example.addressbook.service;

import com.example.addressbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ContactService {


    private final Map<String, Contact> contactMap = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> invertedIndex = new ConcurrentHashMap<>();

    public List<Contact> createContacts(List<Contact> contacts) {
        List<Contact> created = new ArrayList<>();

        for (Contact contact : contacts) {
            String id = UUID.randomUUID().toString();
            contact.setId(id);
            contactMap.put(id, contact);
            indexContact(contact);
            created.add(contact);
        }

        return created;
    }

    public List<Contact> updateContacts(List<Contact> updatedContacts) {
        List<Contact> updatedList = new ArrayList<>();

        for (Contact newContact : updatedContacts) {
            String id = newContact.getId();
            if (id == null || !contactMap.containsKey(id)) {
                continue;
            }

            Contact oldContact = contactMap.get(id);

            removeFromIndex(oldContact);


            if (newContact.getName() != null) {
                oldContact.setName(newContact.getName());
            }
            if (newContact.getPhone() != null) {
                oldContact.setPhone(newContact.getPhone());
            }
            if (newContact.getEmail() != null) {
                oldContact.setEmail(newContact.getEmail());
            }


            indexContact(oldContact);

            updatedList.add(oldContact);
        }

        return updatedList;
    }

    public int deleteContacts(List<String> ids) {
        int count = 0;

        for (String id : ids) {
            Contact contact = contactMap.remove(id);
            if (contact != null) {
                removeFromIndex(contact);
                count++;
            }
        }

        return count;
    }

    public List<Contact> searchContacts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String token = query.trim().toLowerCase();

        Set<String> ids = invertedIndex.getOrDefault(token, Collections.emptySet());

        return ids.stream()
                .map(contactMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void indexContact(Contact contact) {
        tokenize(contact.getName()).forEach(token ->
                invertedIndex
                        .computeIfAbsent(token, k -> ConcurrentHashMap.newKeySet())
                        .add(contact.getId())
        );

        tokenize(contact.getEmail()).forEach(token ->
                invertedIndex
                        .computeIfAbsent(token, k -> ConcurrentHashMap.newKeySet())
                        .add(contact.getId())
        );
    }

    private void removeFromIndex(Contact contact) {
        tokenize(contact.getName()).forEach(token -> {
            Set<String> ids = invertedIndex.get(token);
            if (ids != null) {
                ids.remove(contact.getId());
                if (ids.isEmpty()) invertedIndex.remove(token);
            }
        });

        tokenize(contact.getEmail()).forEach(token -> {
            Set<String> ids = invertedIndex.get(token);
            if (ids != null) {
                ids.remove(contact.getId());
                if (ids.isEmpty()) invertedIndex.remove(token);
            }
        });
    }

    private List<String> tokenize(String input) {
        if (input == null)
            return Collections.emptyList();

        return Arrays.stream(input.toLowerCase().split("[^a-z0-9]+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
