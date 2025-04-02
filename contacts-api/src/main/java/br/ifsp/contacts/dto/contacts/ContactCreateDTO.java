package br.ifsp.contacts.dto.contacts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactCreateDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;
    
    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "Formato de email inválido")
    private String email;
    
    @NotBlank(message = "O telefone não pode estar vazio")
    @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres")
    @Pattern(regexp = "\\d+", message = "O telefone deve conter apenas números")
    private String telefone;
    
    @NotEmpty(message = "O contato deve ter pelo menos um endereço")
    private List<Address> addresses = new ArrayList<>();
    
    public ContactCreateDTO() {
    }
	
	public void convertContactToDTO(Contact contact) {
		BeanUtils.copyProperties(contact, this);
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
