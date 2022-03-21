package com.eddytooth.initial.services;

import java.util.List;

import com.eddytooth.initial.dto.ComentarioDTO;

public interface ComentarioServicio {

	public ComentarioDTO crearComentario(Long idPublicacion, ComentarioDTO comentarioDto);
	
	public List<ComentarioDTO> obtenerComentarioPublicacion (Long idPublicacion);
	
	public ComentarioDTO obtenerComentarioId (Long idComentario, Long idPublicacion);
	
	public List<ComentarioDTO> verComentarioId(Long idComentario);
	
	public ComentarioDTO actualizarComentario(Long idComentario, Long idPublicacion, ComentarioDTO comentarioActualizar);
	
	public void eliminarComentarioId(Long idComentario, Long idPublicacion);
	
}
