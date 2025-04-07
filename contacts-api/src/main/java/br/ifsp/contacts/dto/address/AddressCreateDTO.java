package br.ifsp.contacts.dto.address;

import br.ifsp.contacts.model.Contact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressCreateDTO {
	
	@NotBlank(message="O campo da rua não deve estar vazio")
	private String rua;
	
	@NotBlank(message="O campo da cidade não deve estar vazio")
	private String cidade;
	
	@NotBlank(message="O campo do estado não deve estar vazio")
	@Size(min=2, max=2, message="O campo de estado deve ter apenas a sigla do estado (duas letras)")
	@Pattern(regexp="[A-Z]{2}", message="O campo de estado deve estar em letras maiúscula")
	private String estado;
	
	@NotBlank(message="O campo do cep não deve estar vazio")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
	private String cep;
	
	private Contact contact;
	
	public AddressCreateDTO() {}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
}
