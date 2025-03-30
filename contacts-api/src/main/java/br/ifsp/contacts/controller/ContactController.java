package br.ifsp.contacts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
@Validated
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @Valid @RequestBody Contact updatedContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        existingContact.setNome(updatedContact.getNome());
        existingContact.setEmail(updatedContact.getEmail());
        existingContact.setTelefone(updatedContact.getTelefone());
        existingContact.setAddresses(updatedContact.getAddresses());

        return contactRepository.save(existingContact);
    }

    @PatchMapping("/{id}")
    public Contact updateContactPartial(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "nome":
                    contact.setNome(value);
                    break;
                case "telefone":
                    contact.setTelefone(value);
                    break;
                case "email":
                    contact.setEmail(value);
                    break;
            }
        });

        return contactRepository.save(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Contact> searchContactsByName(@RequestParam String name) {
        return contactRepository.findByNomeContainingIgnoreCase(name);
    }
}