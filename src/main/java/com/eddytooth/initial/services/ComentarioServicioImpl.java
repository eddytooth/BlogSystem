package com.eddytooth.initial.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.eddytooth.initial.dto.ComentarioDTO;
import com.eddytooth.initial.entity.Comentarios;
import com.eddytooth.initial.entity.Publicacion;
import com.eddytooth.initial.exeptions.BlogException;
import com.eddytooth.initial.exeptions.ResouceNotFoundException;
import com.eddytooth.initial.repository.BlogRepository;
import com.eddytooth.initial.repository.ComentarioRepository;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

	@Autowired
	private ComentarioRepository miComentario;

	@Autowired
	private BlogRepository miPublicacion;

	@Autowired
	private ModelMapper modelMapper;

	public ComentarioDTO convertirAdto(Comentarios nuevoComentario) {

		ComentarioDTO elComentario = modelMapper.map(nuevoComentario, ComentarioDTO.class);
		return elComentario;

		/*
		 * ComentarioDTO elComentario = new ComentarioDTO();
		 * elComentario.setEmail(nuevoComentario.getEmail());
		 * elComentario.setMensaje(nuevoComentario.getMensaje());
		 * elComentario.setIdComentario(nuevoComentario.getIdComentario());
		 * elComentario.setNombre(nuevoComentario.getNombre());
		 * 
		 * return elComentario;
		 */
	}

	public Comentarios convertirAEntidad(ComentarioDTO comentarioEnDto) {

		Comentarios comentarioEntidad = modelMapper.map(comentarioEnDto, Comentarios.class);
		return comentarioEntidad;
		/*
		 * Comentarios comentarioEntidad = new Comentarios();
		 * comentarioEntidad.setIdComentario(comentarioEnDto.getIdComentario());
		 * comentarioEntidad.setEmail(comentarioEnDto.getEmail());
		 * comentarioEntidad.setMensaje(comentarioEnDto.getMensaje());
		 * comentarioEntidad.setNombre(comentarioEnDto.getNombre());
		 * 
		 * return comentarioEntidad;
		 */
	}

	@Override
	public ComentarioDTO crearComentario(Long idPublicacion, ComentarioDTO comentarioDto) {
		// TODO Auto-generated method stub
		Comentarios comentario = convertirAEntidad(comentarioDto);

		Publicacion encontrarPublicacion = miPublicacion.findById(idPublicacion)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", idPublicacion));

		comentario.setIdPublicacion(encontrarPublicacion);
		Comentarios nuevoComentario = miComentario.save(comentario);

		return convertirAdto(nuevoComentario);
	}

	@Override
	public List<ComentarioDTO> obtenerComentarioPublicacion(Long idPublicacion) {
		// TODO Auto-generated method stub
		List<Comentarios> comentarios = miComentario.findByIdPublicacionIdPublicacion(idPublicacion);

		return comentarios.stream().map(comentario -> convertirAdto(comentario)).collect(Collectors.toList());
	}

	@Override
	public ComentarioDTO obtenerComentarioId(Long idComentario, Long idPublicacion) {
		// TODO Auto-generated method stub

		Publicacion encontrarPublicacion = miPublicacion.findById(idPublicacion)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", idPublicacion));

		Comentarios comentario = miComentario.findById(idComentario)
				.orElseThrow(() -> new BlogException(HttpStatus.NOT_FOUND, "Comentarioo no encontrado"));

		if (!comentario.getIdPublicacion().getIdPublicacion().equals(encontrarPublicacion.getIdPublicacion())) {
			throw new BlogException(HttpStatus.BAD_REQUEST, "Resource Bad Request");
		}

		List<Comentarios> comment = miComentario.findByIdComentario(idComentario);

		return convertirAdto(comentario);
		// return comment.stream().map(elcomentario ->
		// convertirAdto(elcomentario)).collect(Collectors.toList());
	}

	@Override
	public List<ComentarioDTO> verComentarioId(Long idComentario) {
		// TODO Auto-generated method stub

		List<Comentarios> comment = miComentario.findByIdComentario(idComentario);
		return comment.stream().map(elcomentario -> convertirAdto(elcomentario)).collect(Collectors.toList());
	}

	@Override
	public ComentarioDTO actualizarComentario(Long idComentario, Long idPublicacion,
			ComentarioDTO comentarioActualizar) {
		// TODO Auto-generated method stub

		Publicacion encontrarPublicacion = miPublicacion.findById(idPublicacion)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", idPublicacion));

		Comentarios comentario = miComentario.findById(idComentario)
				.orElseThrow(() -> new BlogException(HttpStatus.NOT_FOUND, "Comentarioo no encontrado"));

		if (!comentario.getIdPublicacion().getIdPublicacion().equals(encontrarPublicacion.getIdPublicacion())) {
			throw new BlogException(HttpStatus.BAD_REQUEST, "Resource Bad Request");
		}

		Comentarios comentarioEdit = comentario;

		comentarioEdit.setNombre(comentarioActualizar.getNombre());
		comentarioEdit.setEmail(comentarioActualizar.getEmail());
		comentarioEdit.setMensaje(comentarioActualizar.getMensaje());
		Comentarios nuevoComentarioActualizable = miComentario.save(comentarioEdit);

		return convertirAdto(nuevoComentarioActualizable);
	}

	@Override
	public void eliminarComentarioId(Long idComentario, Long idPublicacion) {
		// TODO Auto-generated method stub

		Publicacion encontrarPublicacion = miPublicacion.findById(idPublicacion)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", idPublicacion));

		Comentarios comentario = miComentario.findById(idComentario)
				.orElseThrow(() -> new BlogException(HttpStatus.NOT_FOUND, "Comentarioo no encontrado"));

		if (!comentario.getIdPublicacion().getIdPublicacion().equals(encontrarPublicacion.getIdPublicacion())) {
			throw new BlogException(HttpStatus.BAD_REQUEST, "Resource Bad Request");
		}

		miComentario.deleteById(idComentario);

	}

}
