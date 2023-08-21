package uz.agro.users.service;

import uz.agro.users.dto.request.LoginRequest;
import uz.agro.users.dto.response.UserResponse;
import uz.agro.users.helpers.Constants;
import uz.agro.users.helpers.Credentials;
import uz.agro.users.dto.request.UserRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class KeyCloakService {
    @Value("${keycloak.credentials.secret}")
    String CLIENT_SECRET;
    @Autowired
    Keycloak keycloak;
    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<UserResponse> addUser(UserRequest userRequest) {
        CredentialRepresentation credential = Credentials.createPasswordCredentials(userRequest.getPassword());
        UserRepresentation user = modelMapper.map(userRequest, UserRepresentation.class);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        RealmResource realmResource = keycloak.realm(Constants.CUSTOM_REALM.getName());

        UsersResource usersResource = getUsers();
        try (Response response = usersResource.create(user)) {
            if (response.getStatus() == 201) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                CredentialRepresentation passwordCred = new CredentialRepresentation();
                passwordCred.setTemporary(false);
                passwordCred.setType(CredentialRepresentation.PASSWORD);
                passwordCred.setValue(userRequest.getPassword());
                UserResource userResource = usersResource.get(userId);
                userResource.resetPassword(passwordCred);
                RoleRepresentation realmRoleUser = realmResource.roles().get(Constants.CUSTOM_ROLE.getName()).toRepresentation();
                userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));
            }
            return ResponseEntity.ok(modelMapper.map(userRequest, UserResponse.class));
        }
    }

    public ResponseEntity<AccessTokenResponse> login(LoginRequest request) {
        Configuration configuration = new Configuration(
                Constants.SERVER_URL.getName(),
                Constants.CUSTOM_REALM.getName(),
                Constants.CUSTOM_CLIENT.getName(),
                clientCredentials(),
                null
        );
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response = authzClient.obtainAccessToken(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    public void sendVerificationLink(String userId) {
        UsersResource usersResource = getUsers();
        usersResource.get(userId).sendVerifyEmail();
    }

    public void sendResetPassword(String userId) {
        UsersResource usersResource = getUsers();
        usersResource.get(userId).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public ResponseEntity<List<UserRepresentation>> getUserByName(String username) {
        UsersResource usersResource = getUsers();
        List<UserRepresentation> user = usersResource.search(username, true);
        return ResponseEntity.ok(user);
    }

    private UsersResource getUsers() {
        return keycloak.realm(Constants.CUSTOM_REALM.getName()).users();
    }

    private Map<String, Object> clientCredentials() {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put(Constants.SECRET.getName(), CLIENT_SECRET);
        clientCredentials.put(Constants.PROVIDER.getName(), Constants.SECRET.getName());
        return clientCredentials;
    }
}
