package com.owasptesting.controller;

import javax.ws.rs.BadRequestException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.owasptesting.config.KeycloakProvider;
import com.owasptesting.model.User;
import com.owasptesting.service.KeycloakAdminClientService;

@RestController
@RequestMapping("/public")
public class UserRegistrationController {
	Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
	private final KeycloakAdminClientService kcAdminClient;

	private final KeycloakProvider kcProvider;

	public UserRegistrationController(KeycloakAdminClientService kcAdminClient, KeycloakProvider kcProvider) {
		this.kcProvider = kcProvider;
		this.kcAdminClient = kcAdminClient;
	}

	@PostMapping("/create")
	public void createUser(@RequestBody User user) {
	//	try {
			kcAdminClient.createUser(user);
		// return ResponseEntity.ok( );
	//	}catch(DataAccessException e) {
		//	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		//}
	}

	@PostMapping("/login")
	public ResponseEntity<AccessTokenResponse> login(@RequestBody User user) {
		Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(user.getUsername(), user.getPassword())
				.build();

		AccessTokenResponse accessTokenResponse = null;
		try {
			accessTokenResponse = keycloak.tokenManager().getAccessToken();
			return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
		} catch (BadRequestException ex) {
			logger.warn("invalid account. User probably hasn't verified email.", ex);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
		}

	}

	@GetMapping("/")
	public String index() {
		return "Oxygen";
	}

	@GetMapping("/access-denied-response")
	public String accessDenied() {
		return "Access Denied... You don't have permission.";
	}
}
