package br.ifsp.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ifsp.contacts.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	List<Address> findByContactId(Long contactId);
}
