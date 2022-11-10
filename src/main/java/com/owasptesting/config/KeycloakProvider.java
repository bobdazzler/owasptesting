package com.owasptesting.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Configuration
public class KeycloakProvider {

	@Value("${keycloak.auth-server-url}")
	public String serverURL;
	@Value("${keycloak.realm}")
	public String realm;
	@Value("${keycloak.resource}")
	public String clientID;
	@Value("${keycloak.credentials.secret}")
	public String clientSecret;

	private static Keycloak keycloak = null;

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public KeycloakProvider() {
	}

	public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
		return KeycloakBuilder.builder() //
				.realm(realm) //
				.serverUrl(serverURL)//
				.clientId(clientID) //
				.clientSecret(clientSecret) //
				.username(username) //
				.password(password);
	}

	public JsonNode refreshToken(String refreshToken) throws UnirestException {
		String url = serverURL + "/realms/" + realm + "/protocol/openid-connect/token";
		return Unirest.post(url).header("Content-Type", "application/x-www-form-urlencoded")
				.field("client_id", clientID).field("client_secret", clientSecret).field("refresh_token", refreshToken)
				.field("grant_type", "refresh_token").asJson().getBody();
	}

	public Keycloak getInstance() {
		if (keycloak == null) {

			return KeycloakBuilder.builder().realm(realm).serverUrl(serverURL).clientId(clientID)
					.clientSecret(clientSecret).grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
		}
		return keycloak;
	}
}