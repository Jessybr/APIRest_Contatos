package br.ifsp.contacts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.dto.address.AddressCreateDTO;
import br.ifsp.contacts.dto.address.AddressReadDTO;
import br.ifsp.contacts.dto.contacts.ContactReadDTO;
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
    public Page<AddressReadDTO> getAddresses(
    		@RequestParam(defaultValue = "0")int page,
    		@RequestParam(defaultValue = "10")int size,
    		@RequestParam(defaultValue = "id")String sort) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    	Page<Address> addressPage = addressRepository.findAll(pageable);
    	
    	Page<AddressReadDTO> dtoPage = addressPage.map(address -> {
            AddressReadDTO dto = new AddressReadDTO();
            dto.convertAddressToDTO(address);
            return dto;
        });
    	
    	return dtoPage;
    }
    
    @GetMapping("/contacts/{contactId}")
    public List<Address> getAddressesByContact(@PathVariable Long contactId) {
    	ContactReadDTO contactDTO = new ContactReadDTO();
    	
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
        
        contactDTO.convertContactToDTO(contact);
        
        return contactDTO.getAddresses();
    }
    
    @PostMapping("/contacts/{contactId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressCreateDTO createAddress(@PathVariable Long contactId, @RequestBody @Valid AddressCreateDTO addressDTO) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
        
        Address address = new Address();
        address.convertDTOInsertToAddress(addressDTO);
        address.setContact(contact);
        addressDTO.convertAddressToDTO(address);
        return addressDTO;
    }
}
