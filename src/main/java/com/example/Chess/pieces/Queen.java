package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Queen extends Piece {
    public Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean canMove(Position position, Board board) {

        return (new Bishops(this.getPosition()).canMove(position, ) && (Math.abs(getPosition().getX()- position.getX())==1)
                || new Rooks(this.getPosition()).canMove(position, ))&&(Math.abs(getPosition().getX()- position.getX()) + Math.abs(getPosition().getY()- position.getY()) == 1) ;

    }
}
