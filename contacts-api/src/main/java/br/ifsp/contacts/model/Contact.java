package br.ifsp.contacts.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Classe que representa o modelo de dados para um Contato.
 * 
 * @Entity indica que este objeto será mapeado para uma tabela
 * no banco de dados (em cenários de persistência com JPA).
 */
@Entity
public class Contact {

    /**
     * @Id indica que este campo é a chave primária (primary key) da entidade.
     * @GeneratedValue permite que o banco de dados (ou o provedor JPA) 
     * gere automaticamente um valor único para cada novo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    
    /*
     * @OneToMany(mappedBy = "contact"): Indica que um contato pode ter vários endereços.
     * cascade = CascadeType.ALL: Faz com que ao remover um contato, seus endereços também sejam removidos automaticamente.
     * orphanRemoval = true: Remove automaticamente endereços que não estão mais associados a um contato.
     * */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    // Construtor vazio exigido pelo JPA
    public Contact() {}

    // Construtor para facilitar a criação de objetos
    public Contact(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
    
}