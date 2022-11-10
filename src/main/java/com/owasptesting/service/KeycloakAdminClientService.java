package com.owasptesting.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.owasptesting.model.User;

@Service
public class KeycloakAdminClientService {

	@Value("${keycloak.realm}")
	public String realm;
	@Value("${admin-user}")
	private String adminUsername;
	@Value("${admin-password}")
	private String adminPassword;
	@Value("${keycloak.auth-server-url}")
    private String serverUrl;
	@Value("${keycloak-admin-cli}")
	private String admin_cli;
	@Value("${keycloak-realm-master}")
	private String realm_master;
	
	

	public Keycloak getAdminKeycloakUser() {
		return KeycloakBuilder.builder().serverUrl(serverUrl).realm(realm_master).username(adminUsername)
				.password(adminPassword).clientId(admin_cli)
				.resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();

	}

	public UsersResource getRealm() {
		return getAdminKeycloakUser().realm(realm).users();
	}

	private static CredentialRepresentation createPasswordCredentials(String password) {
		CredentialRepresentation passwordCredentials = new CredentialRepresentation();
		passwordCredentials.setTemporary(false);
		passwordCredentials.setType(CredentialRepresentation.PASSWORD);
		passwordCredentials.setValue(password);
		return passwordCredentials;
	}

	public void createUser(User user) {
		CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
		UserRepresentation kcUser = new UserRepresentation();
		kcUser.setUsername(user.getUsername());
		kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
		kcUser.setFirstName(user.getFirstname());
		kcUser.setLastName(user.getLastname());
		kcUser.setEmail(user.getEmail());
		kcUser.setEnabled(true);
		kcUser.setEmailVerified(true);
		kcUser.setClientRoles(null);
		Response createdUserResponse = getRealm().create(kcUser);

		// assigning role to user starts here
		String userId = CreatedResponseUtil.getCreatedId(createdUserResponse);
	//	assignRoleToUser(userId, "users");
		assignRoleToUserRole(userId,"customers");
		
	}
/**
 * 
 * @param userId
 * @param role
 */
	private void assignRoleToUserClient(String userId, String role) {
		Keycloak keycloak = getAdminKeycloakUser();
		UsersResource usersResource = keycloak.realm(realm).users();
		UserResource userResource = usersResource.get(userId);
		// getting client
		ClientRepresentation clientRepresentation = keycloak.realm(realm).clients().findAll().stream()
				.filter(client -> client.getClientId().equals("owasp_testing")).collect(Collectors.toList()).get(0);
		ClientResource clientResource = keycloak.realm(realm).clients().get(clientRepresentation.getId());
		// getting role
		RoleRepresentation roleRepresentation = clientResource.roles().list().stream()
				.filter(element -> element.getName().equals(role)).collect(Collectors.toList()).get(0);
		// assigning to user
		userResource.roles().clientLevel(clientRepresentation.getId()).add(Collections.singletonList(roleRepresentation));
	}
	private void assignRoleToUserRole(String userId, String role) {
		UsersResource userResource = getAdminKeycloakUser().realm(realm_master).users();
		UserResource gettingCreatedUser = userResource.get(userId);
		RoleRepresentation userRealmRole = getAdminKeycloakUser().realm(realm).roles().list().stream()
				.filter(element -> element.getName().equals(role)).collect(Collectors.toList()).get(0);
		gettingCreatedUser.roles().realmLevel().add(Arrays.asList(userRealmRole));
		ClientRepresentation appClient = getAdminKeycloakUser().realm(realm).clients()
				.findByClientId("owasp_testing").get(0);
		RoleRepresentation userClientRole = getAdminKeycloakUser().realm(realm).clients().get(appClient.getId())
				.roles().get("user").toRepresentation();
			gettingCreatedUser.roles().clientLevel(appClient.getId()).add(Arrays.asList(userClientRole));
}
}
