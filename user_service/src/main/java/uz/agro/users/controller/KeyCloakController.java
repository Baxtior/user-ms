package uz.agro.users.controller;

import uz.agro.users.aop.Log;
import uz.agro.users.dto.request.LoginRequest;
import uz.agro.users.dto.request.UserRequest;
import uz.agro.users.dto.response.UserResponse;
import uz.agro.users.service.KeyCloakService;
import jakarta.validation.Valid;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class KeyCloakController {
    @Autowired
    KeyCloakService service;

    @Log
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request){
        return service.addUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody @Valid LoginRequest request){
        return service.login(request);
    }

    @GetMapping("/verification-link/{userId}")
    public ResponseEntity<String> sendVerificationLink(@PathVariable("userId") String userId){
        service.sendVerificationLink(userId);
        return ResponseEntity.ok("Verification Link Send to Registered E-mail Id.");
    }

    @GetMapping("get-user/{username}")
    public ResponseEntity<List<UserRepresentation>> getUserByName(@PathVariable("username") String username){
        return service.getUserByName(username);
    }
}
