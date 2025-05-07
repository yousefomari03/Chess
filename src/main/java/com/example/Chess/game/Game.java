package com.example.Chess.game;

import com.example.Chess.board.Board;
import com.example.Chess.model.Client;
import com.example.Chess.rules.pieces.PiecesSetter;

import java.util.ArrayList;

public abstract class Game {
    public Board board;
    protected PiecesSetter piecesSetter;


    public Game(ArrayList<Client> players){
        this.board = new Board(8, 8);
        this.board.setPlayers(players);
    }

    public final void prepare(){
        setPieces();
        setTimer();
        setRules();
    }

    public void start(){
        piecesSetter.setPiece(board);
    }

    public abstract void setPieces();

    public void setTimer() {
        // TODO: later
    }

    public void setRules() {
        // TODO: later
    }
}
