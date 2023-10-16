package com.quentingenet.openweighttracker.controller;

import com.quentingenet.openweighttracker.EmailValidator;
import com.quentingenet.openweighttracker.GetUserConnectedId;
import com.quentingenet.openweighttracker.dto.appUser.*;
import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.entity.PasswordResetTokenEntity;
import com.quentingenet.openweighttracker.entity.PersonEntity;
import com.quentingenet.openweighttracker.repository.AppUserRepository;
import com.quentingenet.openweighttracker.repository.PasswordResetTokenRepository;
import com.quentingenet.openweighttracker.repository.PersonRepository;
import com.quentingenet.openweighttracker.security.jwt.JwtController;
import com.quentingenet.openweighttracker.security.jwt.JwtFilter;
import com.quentingenet.openweighttracker.security.jwt.JwtUtils;
import com.quentingenet.openweighttracker.service.AppUserServiceImpl;
import com.quentingenet.openweighttracker.service.MailForNewUserServiceImpl;
import com.quentingenet.openweighttracker.service.PasswordResetTokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/users")
public class AppUserController {

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	AppUserServiceImpl appUserServiceImpl;

	@Autowired
	JwtController jwtController;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	GetUserConnectedId getUserConnectedId;

	@Autowired
	PasswordResetTokenServiceImpl passwordResetTokenService;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	MailForNewUserServiceImpl mailForNewUserService;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

	@PostMapping("/add")
	public ResponseEntity<Object> addUser(@Valid @RequestBody NewPersonToSaveDto personToSaveDto) {
		logger.info("POST /users/add");

		AppUserEntity existingUser = appUserRepository.findByAppUsername(personToSaveDto.getAppUsername());
		if (existingUser != null) {
			return new ResponseEntity<>("User already existing", HttpStatus.BAD_REQUEST);
		}
		appUserServiceImpl.saveNewPerson(personToSaveDto);
		logger.info("NEW USER : {}  --> CREATED IN DATABASE", personToSaveDto.getAppUsername());

		Authentication authentication = jwtController.logUser(personToSaveDto.getAppUsername().replaceAll("\\s+",""), personToSaveDto.getPassword().replaceAll("\\s+",""));
		String jwt = jwtUtils.generateToken(authentication);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
		SimpleMailMessage mailToSendForNewUser = mailForNewUserService.constructNewUserEmail(personToSaveDto.getAppUsername());
		mailSender.send(mailToSendForNewUser);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "User registered successfully");
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idUser}")
	public ResponseEntity<Object> deleteAppUser(@PathVariable("idUser") Long idUser, Principal principal) {
		logger.info("DELETE /users/{}", idUser);
		Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);

		AppUserEntity appUserConnected = appUserRepository.findById(appUserConnectedId).orElseThrow(NoSuchElementException::new);
		List<Boolean> isAdmin = appUserConnected.getRoles().stream().map(x -> x.getRoleName().equals("ROLE_ADMIN")).toList();

		try {
			if (appUserConnectedId.equals(idUser)) {
				logger.info("Person is going to be deleted");
				personRepository.deleteById(idUser);
				logger.info("Person with id {} , is deleted now", idUser);
				return new ResponseEntity<>("User is deleted", HttpStatus.OK);
			} else if (isAdmin.get(0).equals(true)) {
				logger.info("Person is going to be deleted BY THE ADMIN");
				personRepository.deleteById(idUser);
				logger.info("Person with id {} , is deleted now BY THE ADMIN", idUser);
				return new ResponseEntity<>("User is deleted", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
			}
		} catch (NoSuchElementException nse) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping("/update/initialdata/{appUserId}")
	public ResponseEntity<Object> updateInitialData(@PathVariable("appUserId") Long appUserId,
																									@RequestBody PersonUpdateInitialDataDto personToUpdateFromUser, Principal principal) {
		logger.info("DELETE /users/update/{}", appUserId);
		Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
		PersonEntity personConnected = personRepository.findById(appUserConnectedId).orElseThrow();
		if (Objects.equals(appUserId, personConnected.getIdPerson())) {
			appUserServiceImpl.updateInitialDataPerson(appUserId, personToUpdateFromUser);
		} else {
			logger.info("BAD USER : UPDATE FOR INITIAL DATA NOT POSSIBLE");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/update/appuser/{appUserId}")
	public ResponseEntity<Object> updateAppUser(@PathVariable("appUserId") Long appUserId,
																							@RequestBody PersonUpdateAppUserDto personToUpdateFromUser, Principal principal) {
		Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
		PersonEntity personConnected = personRepository.findById(appUserConnectedId).orElseThrow();
		if (Objects.equals(appUserId, personConnected.getIdPerson())) {
			appUserServiceImpl.updateAppUserPerson(appUserId, personToUpdateFromUser);
		} else {
			logger.info("BAD USER : UPDATE FOR APPUSER NOT POSSIBLE");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	@RolesAllowed("ADMIN")
	@GetMapping("/all")
	public ResponseEntity<List<AppUserListDto>> getAllAppUsers() {
		logger.info("GET /users/all");
		return new ResponseEntity<>(appUserServiceImpl.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Object> resetPassword(@RequestBody String emailUser, HttpServletRequest request) {
		if (emailValidator.isValidEmail(emailUser)){
			AppUserEntity appUser = appUserRepository.findByEmailUser(emailUser);
			if (appUser== null){
				throw new NullPointerException();
			} else {
				logger.info("USER IS FOUND IN DATA BASE : {}", appUser.getAppUsername());
				String token = UUID.randomUUID().toString();
				passwordResetTokenService.createPasswordResetTokenForUser(appUser, token);
				String requestFormatted = request.getRequestURL().toString().replace("/users/resetPassword", "");
				SimpleMailMessage mailToSend = passwordResetTokenService.constructResetTokenEmail(requestFormatted, token, appUser);
				mailSender.send(mailToSend);
				logger.info("MAIL IS SENT TO : {}", emailUser);
				return ResponseEntity.ok().build();}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/changePassword")
	public ResponseEntity<Object> changePassword(@RequestParam("token") String token, @RequestBody ResetPasswordDto resetPasswordDto) {
		if (resetPasswordDto.getNewPassword() != null && token != null) {
			PasswordResetTokenEntity tokenFound = passwordResetTokenRepository.findByToken(token);
			if (token.equals(tokenFound.getToken())) {
				passwordResetTokenService.changePassword(token, resetPasswordDto.getNewPassword());
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}
}

