package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class Queen extends Piece {
    public Queen(Color color, Position position) {
        super(color, position);
    }
    public Queen(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return (new Bishop(this.getPosition()).canMove(position, board) || new Rook(this.getPosition()).canMove(position, board));
    }
}
