package com.example.Chess.game;

import com.example.Chess.model.Client;
import com.example.Chess.rules.pieces.NormalPiecesSetter;

import java.util.ArrayList;

public class NormalGame extends Game {

    public NormalGame(ArrayList<Client> players) {
        super(players);
    }

    @Override
    public void setPieces() {
        this.piecesSetter = new NormalPiecesSetter();
    }
}
