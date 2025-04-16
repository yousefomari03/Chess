package com.example.Chess.pieces;

import com.example.Chess.enums.Color;

public class Knights extends Piece{

    public Knights(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean canMove(Position position) {
        return false;
    }
}
