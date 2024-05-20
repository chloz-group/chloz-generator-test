package com.chloz.test.web.security.jwt.base;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class TokenProviderBase {

	private final Logger log = LoggerFactory.getLogger(TokenProviderBase.class);

	protected static final String AUTHORITIES_KEY = "auth";

	@Value("${security.authentication.jwt.key}")
	private String keyStr;

	// The token validity in seconds
	@Value("${security.authentication.jwt.validity}")
	private long validity;

	// The token validity for remember me ins seconds
	@Value("${security.authentication.jwt.validityRememberMe}")
	private long validityRememberMe;

	private SecretKey key;
	@PostConstruct
	public void init() {
		byte[] keyBytes = keyStr.getBytes(StandardCharsets.UTF_8);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createToken(Authentication authentication, boolean rememberMe) {
		List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.toList();
		long now = (new Date()).getTime();
		Date validity = null;
		if (rememberMe) {
			validity = new Date(now + this.validityRememberMe * 1000);
		} else {
			validity = new Date(now + this.validity * 1000);
		}
		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.setHeaderParam("typ", "JWT").setIssuedAt(new Date(now)).signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity).compact();
	}

	public Claims validateToken(String authToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken).getBody();
		} catch (JwtException | IllegalArgumentException e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace.", e);
		}
		return null;
	}

}