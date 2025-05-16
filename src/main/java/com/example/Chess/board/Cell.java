package com.example.Chess.board;

import com.example.Chess.pieces.Piece;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cell implements Serializable {
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

    public void fillCell(Boolean isFilled, Piece piece){
        this.isFilled = isFilled;
        this.piece = piece;
    }

}




