package com.eddytooth.initial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eddytooth.initial.entity.Publicacion;

public interface BlogRepository extends JpaRepository<Publicacion, Long>{

}
