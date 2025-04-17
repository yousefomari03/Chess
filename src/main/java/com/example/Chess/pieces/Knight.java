package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Knight extends Piece{

    public Knight(Color color, Position position) {
        super(color, position);
    }
    public Knight(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return checkBoarder(position, board) && (Math.abs(getPosition().getX()-position.getX()) ==1 && (Math.abs(getPosition().getY()-position.getY()))==2)
                ||(Math.abs(getPosition().getX()-position.getX())==2 && Math.abs(getPosition().getY()-position.getY())==1)
                && (canCapture(position, board) || board.isCellEmpty(position));
    }
}
