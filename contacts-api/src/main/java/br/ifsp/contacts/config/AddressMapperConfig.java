package br.ifsp.contacts.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ifsp.contacts.dto.address.AddressReadDTO;
import br.ifsp.contacts.model.Address;

@Configuration
public class AddressMapperConfig {

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
}
            
