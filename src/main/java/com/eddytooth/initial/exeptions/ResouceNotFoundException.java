package com.eddytooth.initial.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ResouceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombreRecurso;
	private String nombreCampo;
	private Long valorCampo;
	
	public ResouceNotFoundException(String nombreRecurso, String nombreCampo, Long valorCampo) {
		super(String.format("%s No Encontrado con : %s '%s' ", nombreRecurso,nombreCampo, valorCampo));
		this.nombreRecurso = nombreRecurso;
		this.nombreCampo = nombreCampo;
		this.valorCampo = valorCampo;
	}
	
	

	
}
