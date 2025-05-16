package com.example.Chess.controllers;

import com.example.Chess.dto.GameStatusDTO;
import com.example.Chess.game.Game;
import com.example.Chess.model.Client;
import com.example.Chess.services.ClientService;
import com.example.Chess.services.JwtService;
import com.example.Chess.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private GameService gameService;

    @PostMapping("/create/{id}")
    public GameStatusDTO createGame(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Map<String, String> times) {
        String senderEmail = jwtService.extractUsername(token.replace("Bearer ", ""));
        Client client = clientService.getClientByEmail(senderEmail);
        return gameService.gameInit(client, id, times);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchGame(@PathVariable String id) {
        return gameService.fetchGame(id);
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<?> joinGame(@RequestHeader("Authorization") String token, @PathVariable String id) {
        String senderEmail = jwtService.extractUsername(token.replace("Bearer ", ""));
        Client client = clientService.getClientByEmail(senderEmail);
        gameService.joinPlayer(id, client);
        return ResponseEntity.ok(gameService.getGameStatus(gameService.getGame(id)));
    }

    @GetMapping("/get-players/{id}")
    public List<Client> getPlayers(@PathVariable String id) {
        return gameService.getPlayers(id);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getGameStatus(@PathVariable String id) {
        Game game = gameService.getGame(id);
        return ResponseEntity.ok(gameService.getGameStatus(game));
    }
}
