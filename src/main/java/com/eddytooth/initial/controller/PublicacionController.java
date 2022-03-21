package com.eddytooth.initial.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eddytooth.initial.dto.PublicacionDTO;
import com.eddytooth.initial.dto.PublicacionResponseDTO;
import com.eddytooth.initial.services.PublicacionServicio;
import com.eddytooth.initial.utils.Constants;

@RestController
@RequestMapping("/api")
public class PublicacionController {

	@Autowired
	private PublicacionServicio miServicio;

	@GetMapping("/listar")
	public List<PublicacionDTO> listarPublicaciones() {
		return miServicio.obtenerPublicacionesBBDD();
	}

	@GetMapping("/listarPaginada")
	public List<PublicacionDTO> listarPublicacionesPaginadas(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer numPage,
			@RequestParam(value = "size", defaultValue = "5", required = true) int cantRegistros) {
		return miServicio.obtenerPublicacionesPaginadas(numPage, cantRegistros);
	}

	@GetMapping("/paginacion")
	public PublicacionResponseDTO publicacionesPaginadas(
			@RequestParam(value = "page", defaultValue = Constants.PAGE_DEFECTO, required = false) Integer numPage,
			@RequestParam(value = "size", defaultValue = Constants.TAMANO, required = true) int cantRegistros,
			@RequestParam(value = "sort", defaultValue = Constants.ORDENAR_POR, required = false) String order,
			@RequestParam(value = "sortBy", defaultValue = Constants.ORDENACION, required = false) String orderBy) {
		return miServicio.otraFormaPaginacion(numPage, cantRegistros, order, orderBy);
	}

	@GetMapping("/listarPor")
	public ResponseEntity<PublicacionDTO> verPorId(@RequestParam(value = "id", defaultValue = "1") Long id) {
		return new ResponseEntity<PublicacionDTO>(miServicio.obtenerPorID(id), HttpStatus.OK);
	}

	@GetMapping("/listarFor/{id}")
	public ResponseEntity<PublicacionDTO> verForId(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(miServicio.obtenerPorID(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO) {
		return new ResponseEntity<PublicacionDTO>(miServicio.crearPublicacion(publicacionDTO), HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,
			@PathVariable Long id) {
		PublicacionDTO publicacionRespuesta = miServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<PublicacionDTO>(publicacionRespuesta, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminar/{id}")
	public void eliminarPublicacion(@PathVariable Long id) {
		miServicio.eliminarPorID(id);
		System.out.println("Publicacion eliminada ok");
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> otraFormaEliminar(@PathVariable Long id) {
		miServicio.eliminarPorID(id);
		return new ResponseEntity<String>("Publicacion Eliminada Exitosamente", HttpStatus.OK);
	}
}
