package com.example.Chess.game;

import com.example.Chess.model.Client;
import com.example.Chess.rules.pieces.NormalPiecesSetter;

import java.io.Serializable;
import java.util.ArrayList;

public class NormalGame extends Game implements Serializable {

    public NormalGame(ArrayList<Client> players, String gameId) {
        super(players, gameId);
    }

    @Override
    public void setPieces() {
        this.piecesSetter = new NormalPiecesSetter();
    }
}
