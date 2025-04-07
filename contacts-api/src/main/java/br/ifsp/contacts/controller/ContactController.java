package br.ifsp.contacts.controller;

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

import br.ifsp.contacts.config.MapperConfig;
import br.ifsp.contacts.dto.contacts.ContactCreateDTO;
import br.ifsp.contacts.dto.contacts.ContactReadDTO;
import br.ifsp.contacts.dto.contacts.ContactUpdateDTO;
import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
@Tag(name = "Contatos", description = "Operações relacionadas a contatos")
@Validated
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Operation(summary = "Buscar todos os contatos paginados")
    @GetMapping
    public Page<ContactReadDTO> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        
    	MapperConfig mapper = new MapperConfig();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        
        Page<ContactReadDTO> dtoPage = contactPage.map(contact -> {
            ContactReadDTO dto = mapper.contactToContactReadDTO(contact);
            return dto;
        });
        
        return dtoPage;
    }

    @Operation(summary = "Buscar contato por ID")
    @GetMapping("{id}")
    public ContactReadDTO getContactById(@PathVariable Long id) {
    	MapperConfig mapper = new MapperConfig();
    	Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
    	
    	ContactReadDTO contactDTO = mapper.contactToContactReadDTO(contact);
    	return contactDTO;
    }

    @Operation(summary = "Cria novo contato")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactCreateDTO createContact(@Valid @RequestBody ContactCreateDTO contactDTO) {
    	MapperConfig mapper = new MapperConfig();
    	Contact contact = mapper.dtoCreateToContact(contactDTO);
        contactRepository.save(contact);
        contactDTO = mapper.contactToContactCreateDTO(contact);
        
        return contactDTO;
    }

    @Operation(summary = "Atualiza contato por ID")
    @PutMapping("/{id}")
    public ContactUpdateDTO updateContact(@PathVariable Long id, @Valid @RequestBody ContactUpdateDTO updatedContactDTO) {
    	MapperConfig mapper = new MapperConfig();
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

        existingContact = mapper.dtoUpdateToContact(updatedContactDTO);
        contactRepository.save(existingContact);
        updatedContactDTO = mapper.contactToContactUpdateDTO(existingContact);

        return updatedContactDTO;
    }

    @Operation(summary = "Atualiza um contato parcialmente")
    @PatchMapping("/{id}")
    public ContactUpdateDTO updateContactPartial(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        MapperConfig mapper = new MapperConfig();
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
        
        ContactUpdateDTO contactUpdate = mapper.contactToContactUpdateDTO(contact);

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
        
        contact = mapper.dtoUpdateToContact(contactUpdate);
        contactRepository.save(contact);
        
        return mapper.contactToContactUpdateDTO(contact); 
    }

    @Operation(summary = "Deleta contato por ID")
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }

    @Operation(summary = "Busca contatos pelo nome")
    @GetMapping("/search")
    public Page<ContactReadDTO> searchContactsByName(
    		@RequestParam String name,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(defaultValue = "id") String sort) {

    	MapperConfig mapper = new MapperConfig();
    	Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    	Page<Contact> contactList = contactRepository.findByNomeContainingIgnoreCase(name, pageable);
    	
    	Page<ContactReadDTO> dtoPage = contactList.map(contact -> {
            ContactReadDTO dto = mapper.contactToContactReadDTO(contact);
            return dto;
        });
        
        return dtoPage;
    }
}