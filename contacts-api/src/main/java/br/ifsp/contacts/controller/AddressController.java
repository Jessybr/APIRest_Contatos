package br.ifsp.contacts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.repository.AddressRepository;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;
	
	
    @GetMapping
    public List<Address> getAllAdderess() {
        return addressRepository.findAll();
    }
	
	/**
	 * Método para obter um endereço pelo id
	 * */
	@GetMapping("/{id}")
	public Address getAddressById(@PathVariable Long id) {
		return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado: " + id));
	}
	
	/**
	 * Método para obter endereços existentes relacionados a um contato, pelo id do contato
	 * */
	@GetMapping("/contacts/{contactId}")
	public List<Address> getAddressesByContact(@PathVariable Long contactId){
		List<Address> addresses = addressRepository.findByContactId(contactId);
		return addresses;
	}
	
	/**
	 * Método para criar um endereço
	 * */
	@PostMapping 
	public Address createAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}
	
	
	/**
	 * Método para editar um endereço pelo id
	 * */
	@PutMapping("/{id}")
	public Address updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
		Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado: " + id));
		
		existingAddress.setRua(updatedAddress.getRua());
		existingAddress.setCidade(updatedAddress.getCidade());
		existingAddress.setCep(updatedAddress.getCep());
		existingAddress.setEstado(updatedAddress.getEstado());
		
		return addressRepository.save(existingAddress);
		
	}
	
	/**
	 * Método para editar parte de um endereço pelo id
	 * */
	@PatchMapping("/{id}")
	public ResponseEntity<Address> updateAddressPartially(@PathVariable Long id, @RequestBody Address updatedAddress) {
	    Address existingAddress = addressRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Endereço não encontrado: " + id));

	    if (updatedAddress.getRua() != null && !updatedAddress.getRua().isBlank()) {
	        existingAddress.setRua(updatedAddress.getRua());
	    }

	    if (updatedAddress.getCidade() != null && !updatedAddress.getCidade().isBlank()) {
	        existingAddress.setCidade(updatedAddress.getCidade());
	    }

	    if (updatedAddress.getCep() != null && !updatedAddress.getCep().isBlank()) {
	        existingAddress.setCep(updatedAddress.getCep());
	    }

	    if (updatedAddress.getEstado() != null && !updatedAddress.getEstado().isBlank()) {
	        existingAddress.setEstado(updatedAddress.getEstado());
	    }

	    Address savedAddress = addressRepository.save(existingAddress);
	    return ResponseEntity.ok(savedAddress);
	}

	
}
