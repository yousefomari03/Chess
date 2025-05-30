package com.example.Chess.controllers;

import com.example.Chess.dto.RegisterDTO;
import com.example.Chess.model.TempRegisterStorage;
import com.example.Chess.security.AuthenticationResponse;
import com.example.Chess.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmailController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/verify/{token}")
    public ResponseEntity<AuthenticationResponse> verifyEmail(@PathVariable String token) {
        RegisterDTO data = TempRegisterStorage.registerStorage.get(token);
        if (data == null) {
            throw new RuntimeException("Expired token");
        }

        return authenticationService.register(TempRegisterStorage.registerStorage.get(token));
    }
}
