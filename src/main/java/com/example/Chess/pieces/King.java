package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

public class King extends Piece {
    public King(Color color, Position position) {
        super(color, position);
    }


    @Override
    public boolean canMove(Position position, Board board) {
        return ;
    }
}
