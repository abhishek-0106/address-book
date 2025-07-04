package com.example.addressbook.storage;

import com.example.addressbook.model.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStorage {

    private final Map<String, Contact> contactMap = new ConcurrentHashMap<>();

    public List<Contact> saveContacts(List<Contact> contacts){
        for (Contact contact : contacts) {
            contactMap.put(contact.getId(), contact);
        }
        return contacts;
    }

    public Contact update(String id, Map<String, String> updates) {
        Contact existing = contactMap.get(id);
        if (existing == null) return null;

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> existing.setName(value);
                case "phone" -> existing.setPhone(value);
                case "email" -> existing.setEmail(value);
            }
        });
        return existing;
    }

    public int delete(List<String> ids) {
        int count = 0;
        for (String id : ids) {
            if (contactMap.remove(id) != null) count++;
        }
        return count;
    }

    public List<Contact> search(String query) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contactMap.values()) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(contact);
            }
        }
        return result;
    }

    public Contact get(String id) {
        return contactMap.get(id);
    }


}
