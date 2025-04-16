package com.example.Chess.pieces;

import com.example.Chess.enums.Color;

public class Pawns extends Piece {

    public Pawns(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean canMove(Position position) {
        return false;
    }
}
