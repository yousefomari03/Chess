package com.example.Chess.game;

import com.example.Chess.board.Board;
import com.example.Chess.model.Client;
import com.example.Chess.rules.pieces.FenPiecesSetter;

import java.io.Serializable;
import java.util.ArrayList;

public class PuzzleGame extends Game implements Serializable {
    private final String fen;

    public PuzzleGame(ArrayList<Client> players, String gameId, String fen) {
        super(players, gameId);
        this.fen = fen;
    }
    
    @Override
    public void setPieces() {
        this.piecesSetter = new FenPiecesSetter(fen);
    }
}
