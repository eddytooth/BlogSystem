package com.eddytooth.initial.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eddytooth.initial.dto.PublicacionDTO;
import com.eddytooth.initial.dto.PublicacionResponseDTO;
import com.eddytooth.initial.entity.Publicacion;
import com.eddytooth.initial.exeptions.ResouceNotFoundException;
import com.eddytooth.initial.repository.BlogRepository;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private BlogRepository blogRepositorio;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDto) {
		// TODO Auto-generated method stub

		Publicacion publicacion = convertiraEntidad(publicacionDto);
		Publicacion guardarPublicacion = blogRepositorio.save(publicacion);
		PublicacionDTO publicacionResponse = convertiraDTO(guardarPublicacion);

		return publicacionResponse;
	}

	private PublicacionDTO convertiraDTO(Publicacion nuevaPublicacion) {

		PublicacionDTO publicacionDTO = modelMapper.map(nuevaPublicacion, PublicacionDTO.class);
		return publicacionDTO;

		/*
		 * Esto lo reemplaza el model mapper PublicacionDTO publicacionDto = new
		 * PublicacionDTO(); publicacionDto.setTitulo(nuevaPublicacion.getTitulo());
		 * publicacionDto.setContenido(nuevaPublicacion.getContenido());
		 * publicacionDto.setDescripcion(nuevaPublicacion.getDescripcion());
		 * publicacionDto.setId(nuevaPublicacion.getIdPublicacion()); return
		 * publicacionDto;
		 */
	}

	private Publicacion convertiraEntidad(PublicacionDTO nuevaPublicDto) {

		Publicacion publicacionEntity = modelMapper.map(nuevaPublicDto, Publicacion.class);
		return publicacionEntity;
		/*
		 * Publicacion publicacionEntity = new Publicacion();
		 * publicacionEntity.setContenido(nuevaPublicDto.getContenido());
		 * publicacionEntity.setDescripcion(nuevaPublicDto.getDescripcion());
		 * publicacionEntity.setTitulo(nuevaPublicDto.getTitulo()); return
		 * publicacionEntity;
		 */
	}

	@Override
	public List<PublicacionDTO> obtenerPublicacionesBBDD() {
		// TODO Auto-generated method stub
		List<Publicacion> publicacionesAlmacenadas = blogRepositorio.findAll();

		return publicacionesAlmacenadas.stream().map(objetoMapeado -> convertiraDTO(objetoMapeado))
				.collect(Collectors.toList());
	}

	@Override
	public List<PublicacionDTO> obtenerPublicacionesPaginadas(int page, int size) {
		// TODO Auto-generated method stub
		Pageable paginacion = PageRequest.of(page, size);
		Page<Publicacion> publicaciones = blogRepositorio.findAll(paginacion);

		List<Publicacion> publicacionesAlmacenadas = publicaciones.getContent();

		return publicacionesAlmacenadas.stream().map(objetoMapeado -> convertiraDTO(objetoMapeado))
				.collect(Collectors.toList());
	}

	@Override
	public PublicacionResponseDTO otraFormaPaginacion(int paginas, int size, String order, String orderFor) {
		// TODO Auto-generated method stub
		Sort ordenacion = orderFor.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(order).ascending()
				: Sort.by(order).descending();

		Pageable paginacion = PageRequest.of(paginas, size, ordenacion);
		Page<Publicacion> publicaciones = blogRepositorio.findAll(paginacion);

		List<Publicacion> publicacionesAlmacenadas = publicaciones.getContent();

		List<PublicacionDTO> contenido = publicacionesAlmacenadas.stream()
				.map(objetoMapeado -> convertiraDTO(objetoMapeado)).collect(Collectors.toList());

		PublicacionResponseDTO response = new PublicacionResponseDTO();
		response.setContenido(contenido);
		response.setPaginacion(publicaciones.getNumber());
		response.setSize(publicaciones.getSize());
		response.setTotalElementos(publicaciones.getTotalElements());
		response.setTotalPaginas(publicaciones.getTotalPages());
		response.setUltima(publicaciones.isLast());

		return response;
	}

	@Override
	public PublicacionDTO obtenerPorID(Long id) {
		// TODO Auto-generated method stub
		Publicacion verPublicacion = blogRepositorio.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", id));
		return convertiraDTO(verPublicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO updatePublish, Long id) {

		Publicacion seePublish = blogRepositorio.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", id));

		seePublish.setTitulo(updatePublish.getTitulo());
		seePublish.setContenido(updatePublish.getContenido());
		seePublish.setDescripcion(updatePublish.getDescripcion());

		Publicacion publicacionActualizada = blogRepositorio.save(seePublish);
		return convertiraDTO(publicacionActualizada);
	}

	@Override
	public void eliminarPorID(Long id) {
		Publicacion encontrarPublicacion = blogRepositorio.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Publicacion", "id", id));

		blogRepositorio.delete(encontrarPublicacion);

	}
}
