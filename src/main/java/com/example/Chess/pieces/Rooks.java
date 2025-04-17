package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Rooks extends Piece {
    public Rooks(Color color, Position position) {
        super(color, position);
    }
    public Rooks(Position position) {
        super(position);
    }


    @Override
    public boolean canMove(Position position, Board board) {

        return((getPosition().getX() ==position.getX()||getPosition().getY()==position.getY())&&!(getPosition().equals(position)));
    }
}
