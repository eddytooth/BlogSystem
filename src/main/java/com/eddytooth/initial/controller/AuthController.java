package com.eddytooth.initial.controller;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eddytooth.initial.dto.LoginDTO;
import com.eddytooth.initial.dto.RegistroDTO;
import com.eddytooth.initial.entity.Rol;
import com.eddytooth.initial.entity.Usuario;
import com.eddytooth.initial.repository.RolRepository;
import com.eddytooth.initial.repository.UsuarioRepository;
import com.eddytooth.initial.security.JwtAuthResponseDTO;
import com.eddytooth.initial.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private RolRepository rolRep;

	@Autowired
	private PasswordEncoder passEnc;

	@Autowired
	private JwtTokenProvider jtp;

	@PostMapping("/inicioSesion")
	public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO login) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsernameOrEmail(), login.getPass()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		// Obtener token del jwt token provider
		String token = jtp.generarToken(auth);

		return ResponseEntity.ok(new JwtAuthResponseDTO(token));

		// return new ResponseEntity<String>("Sesion Iniciada Existosa", HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registro) {
		if (userRepo.existsByUsername(registro.getUsername())) {
			return new ResponseEntity<String>("Usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		if (userRepo.existsByEmail(registro.getEmail())) {
			return new ResponseEntity<String>("Usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		Usuario user = new Usuario();
		user.setUsername(registro.getUsername());
		user.setEmail(registro.getEmail());
		user.setPass(passEnc.encode(registro.getPass()));
		user.setNombre(registro.getNombre());

		Rol role = rolRep.findByNombre("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(role));

		userRepo.save(user);

		return new ResponseEntity<String>("Registro Creado Exitosamente", HttpStatus.OK);
	}
}
