package br.ifsp.contacts.dto.contacts;

import java.util.ArrayList;
import java.util.List;

import br.ifsp.contacts.model.Address;

public class ContactReadDTO {
	
	private Long id;
	private String nome;
    private String email;
    private String telefone;
    private List<Address> addresses = new ArrayList<>();
    
    public ContactReadDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
    
    
}
