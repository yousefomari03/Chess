package com.example.Chess.moves;

import com.example.Chess.board.Board;
import com.example.Chess.pieces.Position;

public interface Move {
    public abstract boolean canMove(Position position, Board board);
}
