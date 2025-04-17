package com.example.Chess.pieces;

import com.example.Chess.enums.Color;

public class Knights extends Piece{

    public Knights(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean canMove(Position position) {
        return (Math.abs(getPosition().getX()-position.getX()) ==1 && (Math.abs(getPosition().getY()-position.getY()))==2)
                ||(Math.abs(getPosition().getX()-position.getX())==2 && Math.abs(getPosition().getY()-position.getY())==1);
    }
}
