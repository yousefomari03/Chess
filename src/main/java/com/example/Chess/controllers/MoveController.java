package com.example.Chess.controllers;

import com.example.Chess.game.Game;
import com.example.Chess.services.game.GameService;
import com.example.Chess.services.moves.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/move")
public class MoveController {
    @Autowired
    private MoveService moveService;
    @Autowired
    private GameService gameService;

    @PostMapping("/apply/{from}/{to}/{gameId}")
    public ResponseEntity<?> applyWithValidation(@PathVariable String from, @PathVariable String to, @PathVariable String gameId) {
        Game game = gameService.getGame(gameId);
        return moveService.applyWithValidation(from , to, game);
    }

    @PostMapping("/bot/apply/{gameId}")
    public ResponseEntity<?> applyWithValidationBot(@PathVariable String gameId) {
        Game game = gameService.getGame(gameId);
        return moveService.applyForBot(game);
    }

    @GetMapping("/validate/{from}/{to}/{gameId}")
    public boolean validate(@PathVariable String from, @PathVariable String to, @PathVariable String gameId) {
        Game game = gameService.getGame(gameId);
        return moveService.validate(from , to, game);
    }

    @PostMapping("/promote/{from}/{to}/{promotionPiece}/{gameId}")
    public ResponseEntity<?> promote(@PathVariable String from, @PathVariable String to, @PathVariable String promotionPiece, @PathVariable String gameId) {
        Game game = gameService.getGame(gameId);
        return moveService.promote(from, to, promotionPiece.charAt(0) + "", game);
    }
}