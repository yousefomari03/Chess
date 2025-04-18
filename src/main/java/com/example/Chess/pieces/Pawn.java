package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Pawn extends Piece {

    public Pawn(Color color, Position position) {
        super(color, position);
    }
    public Pawn(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return  checkBoarder(position, board) && (position.getY() - this.getPosition().getY()) == 1 && (getPosition().getX()==position.getX())
                || ((position.getY() - this.getPosition().getY()) == 2 && (getPosition().getX()==position.getX())&& !(this.isMoved()))
                || (((position.getX() - this.getPosition().getX() == 1 && position.getY() - this.getPosition().getY() == 1) || (position.getX() - this.getPosition().getX() == -1 && position.getY() - this.getPosition().getY() == 1) ) && canCapture(position, board));
    }
}
