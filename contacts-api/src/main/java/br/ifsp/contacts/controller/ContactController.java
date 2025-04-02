package br.ifsp.contacts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import br.ifsp.contacts.dto.contacts.ContactCreateDTO;
import br.ifsp.contacts.dto.contacts.ContactReadDTO;
import br.ifsp.contacts.dto.contacts.ContactUpdateDTO;
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
    public Page<ContactReadDTO> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        
        Page<ContactReadDTO> dtoPage = contactPage.map(contact -> {
            ContactReadDTO dto = new ContactReadDTO();
            dto.convertContactToDTO(contact);
            return dto;
        });
        
        return dtoPage;
    }

    @GetMapping("{id}")
    public ContactReadDTO getContactById(@PathVariable Long id) {
    	Contact contact = new Contact();
    	ContactReadDTO contactDTO = new ContactReadDTO();
    	
    	contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
    	
    	contactDTO.convertContactToDTO(contact);
    	return contactDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactCreateDTO createContact(@Valid @RequestBody ContactCreateDTO contactDTO) {
    	Contact contact = new Contact();
    	contact.convertDTOInsertToContact(contactDTO);
        contactRepository.save(contact);
        contactDTO.convertContactToDTO(contact);
        
        return contactDTO;
    }
 
    @PutMapping("/{id}")
    public ContactUpdateDTO updateContact(@PathVariable Long id, @Valid @RequestBody ContactUpdateDTO updatedContactDTO) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        existingContact.convertDTOUpdateToContact(updatedContactDTO);
        contactRepository.save(existingContact);
        updatedContactDTO.convertContactToDTO(existingContact);

        return updatedContactDTO;
    }

    @PatchMapping("/{id}")
    public ContactUpdateDTO updateContactPartial(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
        
        ContactUpdateDTO contactUpdate = new ContactUpdateDTO();
        contactUpdate.convertContactToDTO(contact);

        updates.forEach((key, value) -> {
            switch (key) {
                case "nome":
                	contactUpdate.setNome(value);
                    break;
                case "telefone":
                	contactUpdate.setTelefone(value);
                    break;
                case "email":
                	contactUpdate.setEmail(value);
                    break;
            }
        });
        
        contact.convertDTOUpdateToContact(contactUpdate);
        contactRepository.save(contact);
        contactUpdate.convertContactToDTO(contact);

        return contactUpdate;
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }

    @GetMapping("/search")
    public Page<ContactReadDTO> searchContactsByName(
    		@RequestParam String name,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(defaultValue = "id") String sort) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    	Page<Contact> contactList = contactRepository.findByNomeContainingIgnoreCase(name, pageable);
    	
    	Page<ContactReadDTO> dtoPage = contactList.map(contact -> {
            ContactReadDTO dto = new ContactReadDTO();
            dto.convertContactToDTO(contact);
            return dto;
        });
        
        return dtoPage;
    }
}