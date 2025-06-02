package com.example.Chess.controllers;

import com.example.Chess.dto.GameStatusDTO;
import com.example.Chess.game.Game;
import com.example.Chess.model.Client;
import com.example.Chess.model.Puzzle;
import com.example.Chess.services.ClientService;
import com.example.Chess.services.JwtService;
import com.example.Chess.services.PuzzleService;
import com.example.Chess.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puzzles")
public class PuzzleController {
    @Autowired
    private PuzzleService puzzleService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/fetch-puzzles/{min}/{max}/{theme}/{opening}")
    public List<Puzzle> fetchPuzzles(@PathVariable int min, @PathVariable int max, @PathVariable String theme, @PathVariable String opening) {
        return puzzleService.getPuzzlesByConditions(min, max, theme, opening);
    }

    @PostMapping("/create")
    public GameStatusDTO createPuzzle(@RequestHeader("Authorization") String token, @RequestBody Puzzle puzzle) {
        String senderEmail = jwtService.extractUsername(token.replace("Bearer ", ""));
        Client client = clientService.getClientByEmail(senderEmail);
        gameService.puzzleGameInit(client, puzzle);
        Game game = gameService.getGame(puzzle.getId());
        Puzzle updatedPuzzle = puzzleService.validateExpectedToWin(game, puzzle);
        gameService.saveGame(game);
        GameStatusDTO status = gameService.getGameStatus(game);
        status.setPuzzle(updatedPuzzle);
        return status;
    }
}