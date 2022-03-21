package com.eddytooth.initial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eddytooth.initial.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmail(String email);

	//public Optional<Usuario> findByUsuarioOrEmail(String usuario, String email);
	
	public Optional<Usuario> findByUsernameOrEmail(String usuario, String email);

	public Optional<Usuario> findByUsername(String usuario);

	public Boolean existsByUsername(String usuario);

	public Boolean existsByEmail(String email);
}
