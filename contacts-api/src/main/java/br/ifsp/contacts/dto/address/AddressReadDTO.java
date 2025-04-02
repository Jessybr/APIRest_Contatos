package br.ifsp.contacts.dto.address;

import org.springframework.beans.BeanUtils;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;

public class AddressReadDTO {
	
	private String rua;
	private String cidade;
	private String estado;
	private String cep;
	private Contact contact;
	
	public AddressReadDTO() {}
	
	public void convertAddressToDTO(Address address) {
		BeanUtils.copyProperties(address, this);
	}

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
