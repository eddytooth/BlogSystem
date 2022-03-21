package com.eddytooth.initial.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eddytooth.initial.entity.Rol;
import com.eddytooth.initial.entity.Usuario;
import com.eddytooth.initial.repository.UsuarioRepository;

//CLASE USADA PARA CARGAR USUARIO
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UsuarioRepository miUsuario;

	@Override
	public UserDetails loadUserByUsername(String usuarioOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		Usuario usuario = miUsuario.findByUsernameOrEmail(usuarioOrEmail, usuarioOrEmail)
				.orElseThrow((() -> new UsernameNotFoundException("User Not Found: " + usuarioOrEmail)));
		return new User(usuario.getEmail(), usuario.getPass(),  mapearRoles(usuario.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles) {
		return roles.stream().map(mirol -> new SimpleGrantedAuthority(mirol.getNombre())).collect(Collectors.toList());
	}

}
