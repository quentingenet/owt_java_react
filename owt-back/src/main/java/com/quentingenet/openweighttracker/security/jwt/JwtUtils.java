package com.quentingenet.openweighttracker.security.jwt;

import java.util.*;
import java.util.stream.Collectors;

import com.quentingenet.openweighttracker.controller.AppUserController;
import com.quentingenet.openweighttracker.entity.Privilege;
import com.quentingenet.openweighttracker.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.repository.AppUserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Autowired
	AppUserRepository appUserRepository;

	long JWT_VALIDITY = 5 * 60 * 60 * 60;

	@Value("${jwt.secret}")
	String secret;

	private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	public String generateToken(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		for(GrantedAuthority authority: authorities){
			if(authority.getAuthority().startsWith("ROLE")){
				roles.add(authority.getAuthority());
			}
		}

		Map<String, Object> claims = new HashMap<>();
		claims.put("roles",roles);
		claims.put("iat",new Date(System.currentTimeMillis()));
		claims.put("exp", new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000));
		claims.put("sub", authentication.getName());

		Map<String, Object> headerJwt = new HashMap<>();
		headerJwt.put("alg", "HS512");
		headerJwt.put("typ", "JWT");

		//TODO Later : Header, claims, jwt... will be Base64urlEncoded
		return Jwts.builder()
				.setHeader(headerJwt)
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		AppUserEntity appUser = appUserRepository.findByAppUsername(claims.getSubject());

		Collection<? extends GrantedAuthority> authorities = getAuthorities(appUser.getRoles());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(
			Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			privileges.add(role.getRoleName());
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
