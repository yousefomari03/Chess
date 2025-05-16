package com.example.Chess.rules.pieces;

import com.example.Chess.board.Board;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public abstract class PiecesSetter implements Serializable {
    public abstract void setPieces(Board board);
}
