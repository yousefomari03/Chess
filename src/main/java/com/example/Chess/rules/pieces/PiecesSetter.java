package com.example.Chess.rules.pieces;

import com.example.Chess.board.Board;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
public abstract class PiecesSetter implements Serializable {
    protected String fen;
    public abstract void setPieces(Board board);
}
