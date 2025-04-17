package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class King extends Piece {
    public King(Color color, Position position) {
        super(color, position);
    }
    public King(Position position) {
        super(position);
    }


    @Override
    public boolean canMove(Position position, Board board) {
        return (new Queen(this.getPosition()).canMove(position, board)) &&
                Math.abs(position.getX() - this.getPosition().getX()) <= 1 && Math.abs(position.getY() - this.getPosition().getY()) <= 1;
    }

    //TODO: check if the king under attack
    //TODO: handle king castling
}
