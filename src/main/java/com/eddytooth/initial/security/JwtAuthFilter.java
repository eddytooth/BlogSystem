package com.eddytooth.initial.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

//Valida todo el token
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jtp;

	@Autowired
	private CustomUserDetailService cuds;

	// Bearer token acceso
	private String obtenerJwtSolicitud(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// obtener token de solicitud hhtp
		String token = obtenerJwtSolicitud(request);

		// validar Token
		if (StringUtils.hasText(token) && jtp.validarToken(token)) {

			// Obtener username del token
			String username = jtp.obtenerUsernameJwt(token);

			// Cargar usuario asociado al token
			UserDetails userDetail = cuds.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetail, null,
					userDetail.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			//Establecer Seguridad
			SecurityContextHolder.getContext().setAuthentication(authToken);
		
		}
		filterChain.doFilter(request, response);
	}

}
