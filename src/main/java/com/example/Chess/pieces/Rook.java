package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Rook extends Piece {
    public Rook(Color color, Position position) {
        super(color, position);
    }
    public Rook(Position position) {
        super(position);
    }


    @Override
    public boolean canMove(Position position, Board board) {

        return checkBoarder(position, board) && ((getPosition().getX() ==position.getX()||getPosition().getY()==position.getY())&&!(getPosition().equals(position)))
               && canPass(position, board) ;
    }
}
