package com.example.Chess.pieces;

import com.example.Chess.enums.Color;

public class Queen extends Piece {
    public Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean canMove(Position position) {

        return (new Bishops(this.getPosition()).canMove(position)||new Rooks(this.getPosition()).canMove(position));
    }
}
