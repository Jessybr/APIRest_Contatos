package br.ifsp.contacts.model;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.ifsp.contacts.dto.address.AddressCreateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * Classe usada como modelo para Endereços
 * */
@Entity
@Table(name = "addresses")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private Long id;
	
    @Column(name = "rua", nullable = false, length = 255)
	private String rua;

    @Column(name = "cidade", nullable = false, length = 255)
	private String cidade;

    @Column(name = "estado", nullable = false, length = 2)
	private String estado;

    @Column(name = "cep", nullable = false, length = 9)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "contactId", nullable = false)
	@JsonBackReference
	private Contact contact;
	
	public Address() {}

	public Address(String rua, String cidade, String estado, String cep, Contact contact) {
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.contact = contact;
	}
	
    public void convertDTOInsertToAddress(AddressCreateDTO addressDTO) {
    	BeanUtils.copyProperties(addressDTO, this);
    }

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
