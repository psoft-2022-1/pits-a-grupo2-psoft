package br.com.ufcg.ccc.psoft.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Data
@Entity
public class Sabor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeSabor;

	private String tipo; //doce ou salgado

	private Double valorMedio;

	private Double valorGrande;
	
	private boolean estaDisponivel;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Collection<Cliente> listeners;

	public Sabor(){}

	public Sabor(String nomeSabor, String tipo, double precoTamMedio, double precoTamGrande) {
		this.nomeSabor = nomeSabor;
		this.tipo = tipo;
		this.valorMedio = precoTamMedio;
		this.valorGrande = precoTamGrande;
		this.estaDisponivel = true;
		this.listeners = new HashSet<>();
	}
	
	public boolean getEstaDisponivel() {
		return this.estaDisponivel;
	}
	public void setEstaDisponivel(boolean estaDisponivel) {
		this.estaDisponivel = estaDisponivel;
	}
	
	public void addListener(Cliente cliente) {
		this.listeners.add(cliente);
	}
	
	public void removeListener(Cliente cliente) {
		this.removeListener(cliente);
	}
	
	public void dispararAviso() {
		
		for(Cliente cliente: this.listeners) {
			System.out.println("Sr(a) " + cliente.getNomeCompleto() + ", O sabor " + this.nomeSabor + " esta disponivel agora!");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeSabor() {
		return nomeSabor;
	}

	public void setNomeSabor(String nomeSabor) {
		this.nomeSabor = nomeSabor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getValorMedio() {
		return valorMedio;
	}

	public void setValorMedio(Double valorMedio) {
		this.valorMedio = valorMedio;
	}

	public Double getValorGrande() {
		return valorGrande;
	}

	public void setValorGrande(Double valorGrande) {
		this.valorGrande = valorGrande;
	}

	public Collection<Cliente> getListeners() {
		return listeners;
	}

	public void setListeners(Collection<Cliente> listeners) {
		this.listeners = listeners;
	}
	
	
}
