package com.example.Chess.moves;

import com.example.Chess.pieces.Position;

public interface Move {
    public abstract boolean canMove(Position position);
}
