package com.eddytooth.initial.security;

import org.springframework.stereotype.Component;

import com.eddytooth.initial.exeptions.BlogException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtTime;

	public String generarToken(Authentication auth) {
		String username = auth.getName();
		Date fechaActual = new Date();
		Date fechaExpiracion = new Date(fechaActual.getTime() + jwtTime);

		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		return token;
	}

	public String obtenerUsernameJwt(String token) {
		Claims claim = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claim.getSubject();
	}

	public Boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			// TODO: handle exception
			throw new BlogException(HttpStatus.BAD_REQUEST, "Firma no Valida");
		} catch (MalformedJwtException e) {
			// TODO: handle exception
			throw new BlogException(HttpStatus.BAD_REQUEST, "Token invalido");
		} catch (ExpiredJwtException e) {
			// TODO: handle exception
			throw new BlogException(HttpStatus.BAD_REQUEST, "Token Caducado");
		} catch (UnsupportedJwtException e) {
			// TODO: handle exception
			throw new BlogException(HttpStatus.BAD_REQUEST, "Token incompatible");
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new BlogException(HttpStatus.BAD_REQUEST, "Cadena Claims Vacia");
		}
	}

}
