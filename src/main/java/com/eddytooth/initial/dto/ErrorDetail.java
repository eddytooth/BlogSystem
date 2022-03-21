package com.eddytooth.initial.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorDetail {

	private Date marcaTiempo;
	private String mensaje;
	private String detalles;
	
	public ErrorDetail(Date marcaTiempo, String mensaje, String detalles) {
		super();
		this.marcaTiempo = marcaTiempo;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}
	
	
	
}
