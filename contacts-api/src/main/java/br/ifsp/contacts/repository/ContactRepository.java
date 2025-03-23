package br.ifsp.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ifsp.contacts.model.Contact;

/**
 * Esta interface extende JpaRepository, que nos fornece métodos prontos 
 * para acessar e manipular dados no banco de dados. Basta especificar 
 * a classe de entidade (Contact) e o tipo da chave primária (Long).
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Podemos adicionar métodos personalizados se necessário.
	
	@Query("SELECT c FROM Contact c WHERE c.nome LIKE CONCAT('%', :nome, '%')")
	List<Contact> findByNome(@Param("nome") String nome);
	
}