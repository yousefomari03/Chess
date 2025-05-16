package com.example.Chess.controllers;

import com.example.Chess.model.Client;
import com.example.Chess.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editor")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/all-clients")
    public List<Client> allClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/get-editor-by-data/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email){
        if (clientService.existsClientByEmail(email)){
            return ResponseEntity.ok(clientService.getClientByEmail(email));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
