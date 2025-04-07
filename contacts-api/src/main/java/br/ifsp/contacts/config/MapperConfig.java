package br.ifsp.contacts.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ifsp.contacts.dto.address.AddressCreateDTO;
import br.ifsp.contacts.dto.address.AddressReadDTO;
import br.ifsp.contacts.dto.contacts.ContactCreateDTO;
import br.ifsp.contacts.dto.contacts.ContactReadDTO;
import br.ifsp.contacts.dto.contacts.ContactUpdateDTO;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;

@Configuration
public class MapperConfig {

        @Bean
        public ModelMapper modelMapper() {
                ModelMapper modelMapper = new ModelMapper();
                return modelMapper;
        }
        
        public AddressReadDTO addressToAddressReadDTO(Address address) {
        	AddressReadDTO addressDTO = new AddressReadDTO();

        	BeanUtils.copyProperties(address, addressDTO);
        	
        	return addressDTO;
        }
        
        public Address dtoReadToAddress(AddressReadDTO dto) {
        	Address address = new Address();
        	
        	BeanUtils.copyProperties(dto, address);
        	
        	return address;
        }
        
        public AddressCreateDTO addressToAddressCreateDTO(Address address) {
        	AddressCreateDTO addressDTO = new AddressCreateDTO();
        	
        	BeanUtils.copyProperties(address, addressDTO);
        	return addressDTO;
        }
        
        public Address dtoCreateToAddress(AddressCreateDTO dto) {
        	Address address = new Address();
        	
        	BeanUtils.copyProperties(dto, address);
        	
        	return address;
        }
        
        public ContactReadDTO contactToContactReadDTO(Contact contact) {
        	ContactReadDTO contactDTO = new ContactReadDTO();

        	BeanUtils.copyProperties(contact, contactDTO);
        	
        	return contactDTO;
        }
        
        public Contact dtoReadToContact(ContactReadDTO dto) {
        	Contact contact = new Contact();
        	
        	BeanUtils.copyProperties(dto, contact);
        	
        	return contact;
        }
        
        public ContactCreateDTO contactToContactCreateDTO(Contact contact) {
        	ContactCreateDTO contactDTO = new ContactCreateDTO();
        	
        	BeanUtils.copyProperties(contact, contactDTO);
        	return contactDTO;
        }
        
        public Contact dtoCreateToContact(ContactCreateDTO dto) {
        	Contact contact = new Contact();
        	
        	BeanUtils.copyProperties(dto, contact);
        	
        	return contact;
        }
        
        public ContactUpdateDTO contactToContactUpdateDTO(Contact contact) {
        	ContactUpdateDTO contactDTO = new ContactUpdateDTO();
        	
        	BeanUtils.copyProperties(contact, contactDTO);
        	return contactDTO;
        }
        
        public Contact dtoUpdateToContact(ContactUpdateDTO dto) {
        	Contact contact = new Contact();
        	
        	BeanUtils.copyProperties(dto, contact);
        	
        	return contact;
        }
}