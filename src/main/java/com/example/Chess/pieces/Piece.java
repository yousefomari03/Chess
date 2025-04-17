package com.example.Chess.pieces;

import com.example.Chess.enums.Color;
import com.example.Chess.moves.Move;


public abstract class Piece implements Move {
    private Position position;
    private Color color;
    private boolean moved;
    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
        this.moved= false;
    }
    public Piece(Position position) {
        this.position = position ;
        this.moved=false;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
