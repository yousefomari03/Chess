package com.example.Chess.services.moves;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.game.Game;
import com.example.Chess.model.CastlingPositions;
import com.example.Chess.moves.Castling;
import com.example.Chess.pieces.*;
import com.example.Chess.services.StockfishService;
import com.example.Chess.services.board.BoardService;
import com.example.Chess.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MoveService {
    @Autowired
    private BoardService boardService;
    @Autowired
    private GameService gameService;

    @Autowired
    private StockfishService stockfishService;

    public Position getPositionFromMove(String move){
        int x = move.charAt(0) - 'a';
        int y = move.charAt(1) - '1';
        return new Position(x, y);
    }

    public String getMoveFromPosition(Position position){
        char x = (char) ('a' + position.getX());
        int y = position.getY() + 1;
        return String.valueOf(x) + y;
    }

    public void updatePassedTime(Game game){
        Board board = game.getBoard();
        ArrayList<Long> lastMove = board.getPlayerLastMoveTimes();
        ArrayList<Integer> passed = board.getPlayerPassedTime();

        int currentPlayer = board.getCurrentPlayer();
        lastMove.set(currentPlayer, System.currentTimeMillis());

        int timeDiff = (int)Math.max(lastMove.get(currentPlayer) - lastMove.get((currentPlayer + 1) % 2), 0);
        if (timeDiff != 0){
            timeDiff /= 1000;
        }
        passed.set(currentPlayer, passed.get(currentPlayer) + timeDiff);
    }

    public ResponseEntity<?> applyWithValidation(String from, String to, Game game){
        if (checkCastlingWithApply(from, to, game)){
            return ResponseEntity.ok(gameService.getGameStatus(game));
        }

        boolean valid = validate(from, to, game);
        boolean isKingOnDistCell = kingOnCell(to, game);
        if (!valid || isKingOnDistCell){
            return ResponseEntity.badRequest().body(gameService.getGameStatus(game));
        }

        ArrayList<String> froms = new ArrayList<>(), tos = new ArrayList<>();
        froms.add(from);
        tos.add(to);
        apply(froms, tos, game);
        return ResponseEntity.ok(gameService.getGameStatus(game));
    }

    public void apply(ArrayList<String> froms, ArrayList<String> tos, Game game){
        for(int i = 0; i < froms.size(); i++){
            String from = froms.get(i);
            String to = tos.get(i);
            Board board = game.getBoard();

            Piece piece =  boardService.getCellFromPosition(getPositionFromMove(from), board).getPiece();
            Position position = getPositionFromMove(to);

            boardService.getCellFromPosition(piece.getPosition(), board).setPiece(null);
            boardService.getCellFromPosition(piece.getPosition(), board).setIsFilled(false);

            boardService.getCellFromPosition(position, board).setPiece(piece);
            boardService.getCellFromPosition(position, board).setIsFilled(true);
            piece.setPosition(position);
            piece.setMoved(true);
        }
        game.getBoard().setStarted(true);
        updatePassedTime(game);
        gameService.updateCurrentTurn(game);
        gameService.saveGame(game);
    }

    public boolean validate(String from, String to, Game game) {
        Board board = game.getBoard();
        Piece piece =  boardService.getCellFromPosition(getPositionFromMove(from), board).getPiece();
        Position position = getPositionFromMove(to);

        return piece.canMove(position, board);
    }

    public ResponseEntity<?> checkThenPromote(String from, String to, String promotionPiece, Game game) {
        Board board = game.getBoard();
        Position position = getPositionFromMove(from);
        Cell cell = boardService.getCellFromPosition(position, board);
        Pawn pawn = (Pawn) cell.getPiece();
        if (pawn.canMove(getPositionFromMove(to), board)){
            return promote(from, to, promotionPiece, game);
        }
        return ResponseEntity.badRequest().body(gameService.getGameStatus(game));
    }

    public ResponseEntity<?> promote(String from, String to, String promotionPiece, Game game) {
        Board board = game.getBoard();
        Position position = getPositionFromMove(from);
        Cell cell = boardService.getCellFromPosition(position, board);
        Pawn pawn = (Pawn) cell.getPiece();
        Piece piece = pawn.getPromotionPiece(promotionPiece.toUpperCase().charAt(0));
        cell.setPiece(piece);
        ArrayList<String> froms = new ArrayList<>(), tos = new ArrayList<>();
        froms.add(from);
        tos.add(to);
        apply(froms, tos, game);
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameStatus(game));
    }

    public boolean checkCastlingWithApply(String from, String to, Game game) {
        Cell cell = boardService.getCellFromPosition(getPositionFromMove(from), game.getBoard());
        if (cell.getPiece() instanceof King king){
            Castling castling = new Castling(king);
            Position position = getPositionFromMove(to);
            if (castling.isCastling(position, game.getBoard())){
                if (castling.canMove(position, game.getBoard())){
                    Rook rook = (Rook) boardService.getCellFromPosition(position, game.getBoard()).getPiece();
                    CastlingPositions castlingPositions = castling.getCastlingPositions(king, rook);
                    ArrayList<String> froms = new ArrayList<>(), tos = new ArrayList<>();
                    froms.add(getMoveFromPosition(king.getPosition()));
                    froms.add(getMoveFromPosition(rook.getPosition()));
                    tos.add(getMoveFromPosition(castlingPositions.getKingPosition()));
                    tos.add(getMoveFromPosition(castlingPositions.getRookPosition()));
                    apply(froms, tos, game);
                    return true;
                }
            }

        }

        return false;
    }

    private boolean kingOnCell(String to, Game game) {
        return boardService.getCellFromPosition(getPositionFromMove(to), game.getBoard()).getPiece() instanceof King;
    }

    public ResponseEntity<?> applyForBot(Game game) {
        ArrayList<String> froms = new ArrayList<>(), tos = new ArrayList<>();

        String best = stockfishService.getBestMove(game.getBoard().getFen(), 10);
        String fromBot = "" + best.charAt(0) + best.charAt(1);
        String toBot = "" + best.charAt(2) + best.charAt(3);
        if (best.length() == 5){
            String promotion = best.charAt(4) + "";
            return promote(fromBot, toBot, promotion, game);
        } else {
            froms.add(fromBot);
            tos.add(toBot);
            apply(froms, tos, game);
            return ResponseEntity.ok(gameService.getGameStatus(game));
        }
    }
}