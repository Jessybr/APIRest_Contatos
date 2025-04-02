
package br.ifsp.contacts.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.ifsp.contacts.dto.contacts.ContactCreateDTO;
import br.ifsp.contacts.dto.contacts.ContactUpdateDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contacts")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "telefone", nullable = false, length = 15)
    private String telefone;
    
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();
    
    public Contact() {
    }
    
    public Contact(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
    
    public void convertDTOInsertToContact(ContactCreateDTO contactDTO) {
    	BeanUtils.copyProperties(contactDTO, this);
    }
    
    public void convertDTOUpdateToContact(ContactUpdateDTO contactDTO) {
    	BeanUtils.copyProperties(contactDTO, this);
    }
    
    public Long getId() {
        return id;
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
        if (addresses != null) {
            addresses.forEach(address -> address.setContact(this)); 
            
            if (this.addresses == null) { 
                this.addresses = new ArrayList<>();
            }
            
            this.addresses.clear(); 
            this.addresses.addAll(addresses);         
        }
    }
}
