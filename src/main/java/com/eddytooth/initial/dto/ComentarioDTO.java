package com.eddytooth.initial.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ComentarioDTO {

	private Long idComentario;

	@NotEmpty
	@Size(min = 2, message = "Nombre incompleto o nulo")
	private String nombre;

	@NotEmpty
	@Email(message = "Email no valido")
	private String email;

	@NotEmpty
	@Size(min = 2, message = "Mensaje errado")
	private String mensaje;

}
