package br.com.ufcg.ccc.psoft.dto;

import java.util.Map;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
@Data
public class SaborDTO {

	private Long id;

	private String nomeSabor;

	private String tipo; 
	
	private Double valorMedio;

	private Double valorGrande;

}
