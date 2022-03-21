package com.eddytooth.initial.dto;

import java.util.List;

import lombok.Data;

@Data
public class PublicacionResponseDTO {
	
	 private List<PublicacionDTO> contenido;
	 private int paginacion;
	 private int size;
	 private Long totalElementos;
	 private int totalPaginas;
	 private boolean ultima;

}
