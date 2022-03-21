package com.eddytooth.initial.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comentarios")
public class Comentarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idComentario;
	
	private String nombre;
	private String email;
	private String mensaje;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPublicacion", nullable = false)
	private Publicacion idPublicacion;
}
