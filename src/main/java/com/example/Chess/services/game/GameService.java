package com.example.Chess.services.game;

import com.example.Chess.board.Board;
import com.example.Chess.dto.ClientDTO;
import com.example.Chess.dto.GameOverDTO;
import com.example.Chess.dto.GameStatusDTO;
import com.example.Chess.enums.Color;
import com.example.Chess.game.Game;
import com.example.Chess.game.NormalGame;
import com.example.Chess.game.PuzzleGame;
import com.example.Chess.model.Client;
import com.example.Chess.model.Puzzle;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.services.ClientService;
import com.example.Chess.services.PiecesService;
import com.example.Chess.services.PuzzleService;
import com.example.Chess.services.check.CheckService;
import com.example.Chess.services.moves.MoveService;
import com.example.Chess.utils.FileSystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GameService {
    @Autowired
    private FileSystemUtil fileSystemUtil;

    @Autowired
    private ClientService clientService;

    public GameStatusDTO gameInit(Client client, String gameId, Map<String, String> times){
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(client);
        Game game = new NormalGame(clients, gameId);
        int secondsPerSide = Integer.parseInt(times.get("time"));
        int increments = Integer.parseInt(times.get("increments")); // TODO: increment per moves
        game.getBoard().setSecondsPerSide(secondsPerSide);
        saveGame(game);
        return getGameStatus(game);
    }

    public GameStatusDTO gameBotInit(Client client, String gameId, Map<String, String> times){
        ArrayList<Client> clients = new ArrayList<>();
        Client bot = clientService.getClientByEmail("chessBotChecks@gmail.com");
        clients.add(client);
        clients.add(bot);
        Game game = new NormalGame(clients, gameId);
        game.prepare();
        System.out.println(game.getBoard().getFen());
        game.getBoard().setSecondsPerSide(Integer.MAX_VALUE);
        saveGame(game);
        return getGameStatus(game);
    }

    public void joinPlayer(String gameId, Client client) {
        Game game = (Game) fileSystemUtil.readObjectFromFile(gameId);
        if (game == null) {
            throw new RuntimeException("Error: Game not found");
        }
        game.getBoard().getPlayers().add(client);
        game.prepare();
        System.out.println(game.getBoard().getFen());
        saveGame(game);
    }

    public void saveGame(Game game) {
        fileSystemUtil.writeObjectOnFile(game, game.getId());
    }

    public Game getGame(String gameId) {
        return (Game) fileSystemUtil.readObjectFromFile(gameId);
    }

    public List<Client> getPlayers(String id) {
        Game game = (Game) fileSystemUtil.readObjectFromFile(id);
        if (game == null) {
            System.out.println("Error: Game not found");
            throw new RuntimeException("Error: Game not found");
        }

        return game.getBoard().getPlayers();
    }

    public ResponseEntity<?> fetchGame(String id) {
        Game game = (Game) fileSystemUtil.readObjectFromFile(id);
        if (game == null) {
            return ResponseEntity.badRequest().body("Error: Game not found");
        }
        return ResponseEntity.ok(getGameStatus(game));
    }

    public void updateCurrentTurn(Game game){
        Board board = game.getBoard();
        board.setCurrentPlayer((board.getCurrentPlayer() + 1) % 2);
        saveGame(game);
    }

    public GameOverDTO checkGameOver(Game game) {
        Board board = game.getBoard();
        if (board.getPlayers().size() >= 2) {
            ArrayList<Client> players = board.getPlayers();
            Client player1 = players.get(0);
            if (CheckService.checkmate(0, board)) {
                return GameOverDTO.builder()
                        .over(true)
                        .winner(
                            ClientDTO.builder()
                                    .name(player1.getName())
                                    .email(player1.getEmail())
                                    .id(player1.getId())
                                    .turn(0)
                                    .build()
                        ).reason("Checkmate")
                        .build();
            }
            Client player2 = players.get(1);
            if (CheckService.checkmate(1, board)) {
                System.out.println("White checkmated");
                return GameOverDTO.builder()
                        .over(true)
                        .winner(
                                ClientDTO.builder()
                                        .name(player2.getName())
                                        .email(player2.getEmail())
                                        .id(player2.getId())
                                        .turn(1)
                                        .build()
                        ).reason("Checkmate")
                        .build();
            }
            if (calculateRemainingTime(game, 0) == 0){
                return GameOverDTO.builder()
                        .over(true)
                        .winner(
                                ClientDTO.builder()
                                        .name(player1.getName())
                                        .email(player1.getEmail())
                                        .id(player1.getId())
                                        .turn(1)
                                        .build()
                        ).reason("Time out")
                        .build();
            }
            if (calculateRemainingTime(game, 1) == 0){
                return GameOverDTO.builder()
                        .over(true)
                        .winner(
                                ClientDTO.builder()
                                        .name(player2.getName())
                                        .email(player2.getEmail())
                                        .id(player2.getId())
                                        .turn(1)
                                        .build()
                        ).reason("Time out")
                        .build();
            }
        }

        return GameOverDTO.builder().over(false).winner(null).build();
    }

    public GameStatusDTO getGameStatus(Game game){
        GameOverDTO gameOver = checkGameOver(game);
        return GameStatusDTO.builder()
                .fen(game.getBoard().getFen())
                .gameOver(gameOver.isOver())
                .winner(gameOver.getWinner())
                .currentTurn(game.getBoard().getCurrentPlayer())
                .numOfPlayers(game.getBoard().getPlayers().size())
                .whiteTimeLeft(calculateRemainingTime(game, 0))
                .blackTimeLeft(calculateRemainingTime(game, 1))
                .started(game.getBoard().isStarted())
                .gameOverReason(gameOver.getReason())
                .build();
    }

    private int calculateRemainingTime(Game game, int player){
        Board board = game.getBoard();
        int diffTime = (int) Math.max((System.currentTimeMillis() - board.getPlayerLastMoveTimes().get((player + 1) % 2)), 0L);
        if (diffTime != 0) {
            diffTime /= 1000;
        }

        return Math.max(board.getSecondsPerSide() - board.getPlayerPassedTime().get(player) - diffTime, 0);
    }

    public void puzzleGameInit(Client client, Puzzle puzzle) {
        ArrayList<Client> clients = new ArrayList<>();
        Client bot = clientService.getClientByEmail("chessBotChecks@gmail.com");
        clients.add(client);
        clients.add(bot);
        Game game = new PuzzleGame(clients, puzzle.getId(), puzzle.getFen());
        game.prepare();

        saveGame(game);
    }
}