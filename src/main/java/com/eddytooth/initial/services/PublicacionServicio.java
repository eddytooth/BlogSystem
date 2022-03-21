package com.eddytooth.initial.services;

import java.util.List;

import com.eddytooth.initial.dto.PublicacionDTO;
import com.eddytooth.initial.dto.PublicacionResponseDTO;

public interface PublicacionServicio {

	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDto);

	public List<PublicacionDTO> obtenerPublicacionesBBDD();

	public List<PublicacionDTO> obtenerPublicacionesPaginadas(int paginas, int size);

	public PublicacionResponseDTO otraFormaPaginacion(int paginas, int size, String order, String orderFor);

	public PublicacionDTO obtenerPorID(Long id);

	public PublicacionDTO actualizarPublicacion(PublicacionDTO updatePublish, Long id);

	public void eliminarPorID(Long id);

}
