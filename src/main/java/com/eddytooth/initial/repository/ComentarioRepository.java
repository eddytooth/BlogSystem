package com.eddytooth.initial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eddytooth.initial.dto.ComentarioDTO;
import com.eddytooth.initial.entity.Comentarios;

public interface ComentarioRepository extends JpaRepository<Comentarios, Long>{
		
	public List<Comentarios> findByIdPublicacionIdPublicacion(Long idPublicacion);
	
	public List<Comentarios> findByIdComentario(Long idComentario);

}
