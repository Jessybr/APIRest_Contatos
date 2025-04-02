package br.ifsp.contacts.dto.contacts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactUpdateDTO {
    private String nome;
    
    @Email(message = "Formato de email inválido")
    private String email;
    
    @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres")
    @Pattern(regexp = "\\d+", message = "O telefone deve conter apenas números")
    private String telefone;
    
    private List<Address> addresses = new ArrayList<>();
    
    public ContactUpdateDTO() {}
    
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
