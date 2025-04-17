package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Pawns extends Piece {

    public Pawns(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return  (position.getY() - this.getPosition().getY()) == 1 && (getPosition().getX()==position.getX()) ||
        (position.getY() - this.getPosition().getY()) == 2 && (getPosition().getX()==position.getX())&& !(this.isMoved());
    }
}
