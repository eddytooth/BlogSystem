package com.eddytooth.initial.dto;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.eddytooth.initial.entity.Comentarios;

@Data
public class PublicacionDTO {

	private Long id;

	@NotEmpty
	@Size(min = 2, message = "Valor Incorrecto")
	private String titulo;

	@NotEmpty
	@Size(min = 10, message = "Valor Incorrecto")
	private String descripcion;

	@NotEmpty
	@Size(min = 2, message = "Valor Incorrecto")
	private String contenido;

	private Set<Comentarios> comentario;

}
