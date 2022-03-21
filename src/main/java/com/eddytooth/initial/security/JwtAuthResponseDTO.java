package com.eddytooth.initial.security;

import lombok.Data;

@Data
public class JwtAuthResponseDTO {

	private String tokenAcceso;
	private String tipoToken = "Bearer";

	public JwtAuthResponseDTO(String tokenAcceso) {
		this.tokenAcceso = tokenAcceso;
	}

}
