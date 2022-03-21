package com.eddytooth.initial.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eddytooth.initial.dto.ComentarioDTO;
import com.eddytooth.initial.services.ComentarioServicio;

@RestController
@RequestMapping("/coments")
public class ComentarioController {

	@Autowired
	private ComentarioServicio invocarServicioComentario;

	@GetMapping("/publicacion/{idPublicacion}/find")
	public List<ComentarioDTO> seeComents(@PathVariable(value = "idPublicacion") Long idPublicacion) {
		return invocarServicioComentario.obtenerComentarioPublicacion(idPublicacion);
	}

	@GetMapping("/publicacion/findById/{com}/{pub}")
	public ResponseEntity<ComentarioDTO> seeComentsForId(@PathVariable(name = "com", required = true) Long idComentario,
			@PathVariable(name = "pub", required = true) Long idPublicacion) {

		ComentarioDTO nuevo = invocarServicioComentario.obtenerComentarioId(idComentario, idPublicacion);

		return new ResponseEntity<ComentarioDTO>(nuevo, HttpStatus.OK);
		// return invocarServicioComentario.obtenerComentarioId(idComentario,
		// idPublicacion);
	}

	@GetMapping("/publicacion/buscarById")
	public List<ComentarioDTO> verComentsForId(
			@RequestParam(name = "com", defaultValue = "1", required = true) Long idComentario) {

		return invocarServicioComentario.verComentarioId(idComentario);
	}

	@PostMapping("/publicacion/{idPublicacion}/addComment")
	public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "idPublicacion") Long idPublicacion,
			@Valid @RequestBody ComentarioDTO comentDto) {
		return new ResponseEntity<ComentarioDTO>(invocarServicioComentario.crearComentario(idPublicacion, comentDto),
				HttpStatus.CREATED);
	}

	@PutMapping("/publicacion/updateById/{com}/{pub}")
	public ResponseEntity<ComentarioDTO> updateComentsForId(
			@Valid @PathVariable(name = "com", required = true) Long idComentario,
			@PathVariable(name = "pub", required = true) Long idPublicacion,
			@RequestBody ComentarioDTO nuevoComentario) {

		ComentarioDTO nuevo = invocarServicioComentario.actualizarComentario(idComentario, idPublicacion,
				nuevoComentario);

		return new ResponseEntity<ComentarioDTO>(nuevo, HttpStatus.OK);

	}

	@DeleteMapping("/publicacion/delete/{com}/{pub}")
	public ResponseEntity<String> eliminarComentario(@PathVariable(name = "com", required = true) Long idComentario,
			@PathVariable(name = "pub", required = true) Long idPublicacion) {

		invocarServicioComentario.eliminarComentarioId(idComentario, idPublicacion);

		return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);

	}

}
