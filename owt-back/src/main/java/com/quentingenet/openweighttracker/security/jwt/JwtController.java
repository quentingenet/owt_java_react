package com.quentingenet.openweighttracker.security.jwt;

import com.quentingenet.openweighttracker.controller.AppUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.userdetails.User;

import com.quentingenet.openweighttracker.security.configuration.MyUserDetailService;

import java.util.Collections;

import static com.quentingenet.openweighttracker.security.jwt.JwtFilter.AUTHORIZATION_HEADER;


@RestController
public class JwtController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;

	private final Logger logger = LoggerFactory.getLogger(JwtController.class);
	@PostMapping("/login")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
		Authentication authentication = logUser(jwtRequest.getAppUsername(), jwtRequest.getPassword());
		String jwt = jwtUtils.generateToken(authentication);
		Object principal = authentication.getPrincipal();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);
		httpHeaders.setAccessControlAllowHeaders(Collections.singletonList("Authorization"));
		return new ResponseEntity<>(new JwtResponse(((User) principal).getUsername()), httpHeaders, HttpStatus.OK);
	}

	public Authentication logUser(String appUsername, String password) {
		//CETTE LIGNE EN DESSOUS EST A REVOIR CAR NE GENERE PAS DE JWT
		Authentication authentication = authenticationManagerBuilder.getObject()
				.authenticate(new UsernamePasswordAuthenticationToken(appUsername, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}
}
