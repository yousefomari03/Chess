package com.example.Chess.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cell {
    private String name;
    private Boolean isFilled;


    public Cell(String name, Boolean isFilled) {
        this.name = name;
        this.isFilled = isFilled;
    }





}




