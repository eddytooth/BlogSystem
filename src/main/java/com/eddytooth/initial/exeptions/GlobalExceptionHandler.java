package com.eddytooth.initial.exeptions;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eddytooth.initial.dto.ErrorDetail;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResouceNotFoundException.class)
	public ResponseEntity<ErrorDetail> manejarResourceNotFounException(ResouceNotFoundException excep, WebRequest wr) {
		ErrorDetail errorDetalle = new ErrorDetail(new Date(), excep.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorDetail>(errorDetalle, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogException.class)
	public ResponseEntity<ErrorDetail> manejarBlogException(BlogException excep, WebRequest wr) {
		ErrorDetail errorDetalle = new ErrorDetail(new Date(), excep.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorDetail>(errorDetalle, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> manejarException(Exception excep, WebRequest wr) {
		ErrorDetail errorDetalle = new ErrorDetail(new Date(), excep.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorDetail>(errorDetalle, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub

		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String nombreCampo = ((FieldError) error).getField();
			String mensaje = error.getDefaultMessage();
			errores.put(nombreCampo, mensaje);
		});
		return new ResponseEntity<Object>(errores, HttpStatus.BAD_REQUEST);
	}
}
