package com.rehmanmsit.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rehmanmsit.security.AppUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
*
* @author Rehman
*
*/

@Service
public class JWTUtil {
	
	@Value("${jwtUtil.secretKey}")
	private String secretKey;
	
	@Value("${jwtUtil.expiration}")
	private Long expiration; //in second
	
	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public Object getCliamFromToken(String token, Object cliam) {
		return getAllClaimsFromToken(token).get(cliam);
	}
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(AppUser user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", user.getRoles());
		if(null != user.getFields()) {
			claims.put("name", user.getFields().get("name"));
		}
		return doGenerateToken(claims, user.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {

		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(key)
				.compact();
	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}
