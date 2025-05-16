package com.example.Chess.game;

import com.example.Chess.board.Board;
import com.example.Chess.model.Client;
import com.example.Chess.rules.pieces.PiecesSetter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@ToString

public abstract class Game implements Serializable {
    private String id;
    private Board board;
    protected PiecesSetter piecesSetter;

    public Game(ArrayList<Client> players, String gameId){
        this.id = gameId;
        this.board = new Board(8, 8, players);
        this.board.setPlayers(players);
    }

    public final void prepare(){
        setPieces();
        setTimer();
        setRules();
        piecesSetter.setPieces(board);
    }

    public void start(){
        piecesSetter.setPieces(board);
    }

    public abstract void setPieces();

    public void setTimer() {
        // TODO: later
    }

    public void setRules() {
        // TODO: later
    }
}
