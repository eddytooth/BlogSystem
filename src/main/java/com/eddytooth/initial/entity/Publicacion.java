package com.eddytooth.initial.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo" }) })
@Getter
@Setter
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPublicacion;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(nullable = false)
	private String contenido;

	@JsonBackReference
	@OneToMany(mappedBy = "idPublicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comentarios> comentario = new HashSet<>();

	public Publicacion() {
		super();
	}

	public Publicacion(Long idPublicacion, String titulo, String descripcion, String contenido,
			Set<Comentarios> comentario) {
		super();
		this.idPublicacion = idPublicacion;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
		this.comentario = comentario;
	}


}
