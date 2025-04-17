package com.example.Chess.services;

import com.example.Chess.model.Client;
import com.example.Chess.model.Token;
import com.example.Chess.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenService tokenService;

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No client found with id: " + id)
        );
    }

    public boolean existsClientByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    public Client getClientByEmail(String email){
        return clientRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("No client found with email: " + email)
        );
    }

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(editor -> {
            editor.setName(updatedClient.getName());
            editor.setPassword(updatedClient.getPassword());
            return clientRepository.save(editor);
        }).orElseThrow(() -> new NoSuchElementException("Client with ID " + id + " not found"));

    }

    public void deleteEditor(Long id) {
        if (clientRepository.existsById(id)) {
            Client client = clientRepository.findById(id).orElseThrow();
            List<Token> tokens = tokenService.findAllValidTokenClient(id);
            tokenService.deleteAll(tokens);
            clientRepository.deleteById(id);
        }
    }

    public void deleteClient(Long id){
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Client with ID " + id + " not found");
        }
        clientRepository.deleteById(id);
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client getClientToShareWith(String reqToken, Long ownerId, String email) {
        String senderEmail = jwtService.extractUsername(reqToken.replace("Bearer ", ""));
        Client client = getClientByEmail(senderEmail);
        if (!Objects.equals(client.getId(), ownerId)){
            throw new IllegalArgumentException("You are not allowed to share the project");
        }
        return getClientByEmail(email);
    }
}