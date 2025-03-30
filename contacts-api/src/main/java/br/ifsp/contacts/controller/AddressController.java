package br.ifsp.contacts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @GetMapping
    public List<Address> getAddresses() {
    	return addressRepository.findAll();
    }
    
    @GetMapping("/contacts/{contactId}")
    public List<Address> getAddressesByContact(@PathVariable Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
        
        return contact.getAddresses();
    }
    
    @PostMapping("/contacts/{contactId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@PathVariable Long contactId, @RequestBody @Valid Address address) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
        
        address.setContact(contact);
        return addressRepository.save(address);
    }
}
