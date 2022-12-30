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
import lombok.Getter;
import lombok.Setter;

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
}
