package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Bishops extends Piece {
    public Bishops(Color color, Position position) {
        super(color, position);
    }
    public Bishops(Position position) {
        super(position);
    }


    @Override
    public boolean canMove(Position position, Board board) {
        return Math.abs(getPosition().getX()-position.getX())==Math.abs(getPosition().getY()-position.getY())
                && Math.abs(getPosition().getY()-position.getY())!=0;
    }
}
