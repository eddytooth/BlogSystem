package com.eddytooth.initial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eddytooth.initial.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol> findByNombre(String nombre);
}
