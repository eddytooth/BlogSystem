package com.eddytooth.initial.exeptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus estado;
	private String mensaje;

	public BlogException(HttpStatus estado, String mensaje) {
		super(String.format("%s No Encontrado con : ", mensaje));
		this.estado = estado;
		this.mensaje = mensaje;
	}
}
