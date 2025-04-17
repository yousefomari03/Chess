package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Bishop extends Piece {
    public Bishop(Color color, Position position) {
        super(color, position);
    }
    public Bishop(Position position) {
        super(position);
    }


    @Override
    public boolean canMove(Position position, Board board) {
        return checkBoarder(position, board) && Math.abs(getPosition().getX()-position.getX())==Math.abs(getPosition().getY()-position.getY())
                && Math.abs(getPosition().getY()-position.getY())!=0 && canPass(position, board);
    }
}
