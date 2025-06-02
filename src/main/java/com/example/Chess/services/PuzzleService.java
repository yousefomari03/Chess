package com.example.Chess.services;

import com.example.Chess.board.Board;
import com.example.Chess.dto.GameStatusDTO;
import com.example.Chess.enums.Color;
import com.example.Chess.game.Game;
import com.example.Chess.game.PuzzleGame;
import com.example.Chess.model.Puzzle;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.repository.PuzzleRepository;
import com.example.Chess.services.game.GameService;
import com.example.Chess.services.moves.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PuzzleService {
    @Autowired
    private PuzzleRepository puzzleRepository;

    @Autowired
    private MoveService moveService;

    @Autowired
    private GameService gameService;

    public void add(Puzzle puzzle){
        puzzleRepository.save(puzzle);
    }

    public boolean remove(Puzzle puzzle){
        if (findById(puzzle.getId()) != null){
            puzzleRepository.delete(puzzle);
            return true;
        }

        return false;
    }

    public List<Puzzle> findAll(){
        return puzzleRepository.findAll();
    }

    public Puzzle findById(String id){
        return puzzleRepository.findById(id).orElse(null);
    }

    public List<Puzzle> findByRating(int min, int max){
        return puzzleRepository.findByRatingBetween(min, max);
    }

    public boolean existsById(String id){
        return puzzleRepository.existsById(id);
    }

    public List<Puzzle> findByTheme(String theme){
        return puzzleRepository.findByThemesContaining(theme);
    }

    public List<Puzzle> findByOpening(String opening){
        return puzzleRepository.findByOpeningContainingIgnoreCase(opening);
    }

    public Puzzle findRandomPuzzle(){
        return puzzleRepository.findRandomPuzzle();
    }

    public List<Puzzle> getPuzzlesByConditions(int min, int max, String theme, String opening) {
        List<Puzzle> puzzles = findByRating(min, max);

        if (!theme.equals("default")){
            puzzles.retainAll(findByTheme(theme));
        }

        if (!opening.equals("default")) {
            puzzles.retainAll(findByOpening(opening));
        }

        return puzzles;
    }

    public Puzzle validateExpectedToWin(Game game, Puzzle puzzle) {
        Board board = game.getBoard();
        List<String> moves = puzzle.getSolutionMoves();

        Game testGame = new PuzzleGame(board.getPlayers(), puzzle.getId(), puzzle.getFen());
        testGame.prepare();
        ArrayList<String> froms = new ArrayList<>();
        ArrayList<String> tos = new ArrayList<>();

        for(int i = 0; i < moves.size() - 1; i++){
            String move = moves.get(i);
            String from = move.charAt(0) + "" + move.charAt(1);
            String to = move.charAt(2) + "" + move.charAt(3);

            froms.add(from);
            tos.add(to);
        }

        moveService.apply(froms, tos, testGame);

        String lastMove = moves.get(moves.size() - 1);
        String firstCell = lastMove.charAt(0) + "" + lastMove.charAt(1);
        Position position = moveService.getPositionFromMove(firstCell);
        System.out.println("The position for the last move: " + position);

        Piece piece = testGame.getBoard().getChessBoard()[position.getX()][position.getY()].getPiece();
        System.out.println("The piece to be moved: " + piece);

        System.out.println("The current fen of the game: " + game.getBoard().getFen());
        int turn = piece.getColor() == Color.Black ? 1 : 0;

        int toMove = puzzle.getFen().split(" ")[1].equals("w") ? 0 : 1;

        if (turn != toMove){
            froms = new ArrayList<>();
            tos = new ArrayList<>();
            String move = moves.get(0);
            String from = move.charAt(0) + "" + move.charAt(1);
            String to = move.charAt(2) + "" + move.charAt(3);
            System.out.println("moving from: " + from + " to " + to);
            froms.add(from);
            tos.add(to);
            System.out.println("Before " + game.getBoard().getFen());
            moveService.apply(froms, tos, game);
            puzzle.setCurrentMove(puzzle.getCurrentMove() + 1);
            System.out.println("After " + game.getBoard().getFen());
            board.setCurrentPlayer(turn);
        }

        return puzzle;
    }
}