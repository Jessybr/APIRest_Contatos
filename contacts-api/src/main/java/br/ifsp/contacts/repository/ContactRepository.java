package br.ifsp.contacts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ifsp.contacts.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	Page<Contact> findAll(Pageable pageable);
	
	Page<Contact> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
}