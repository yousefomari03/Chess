package com.example.Chess.board;

import com.example.Chess.pieces.Piece;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cell {
    private String name;
    private Boolean isFilled;
    private Piece piece;


    public Cell(String name, Boolean isFilled) {
        this.name = name;
        this.isFilled = isFilled;
    }

    public Cell(Boolean isFilled, Piece piece) {
        this.isFilled = isFilled;
        this.piece = piece;
    }

}




