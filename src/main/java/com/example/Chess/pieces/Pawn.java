package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.services.check.Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color, Position position) {
        super(color, position);
    }
    public Pawn(Position position) {
        super(position);
    }
    @Override
    public boolean canMove(Position position, Board board) {
        return  (checkBoarder(position, board) && (position.getY() - this.getPosition().getY()) == 1 && (getPosition().getX()==position.getX())
                || ((position.getY() - this.getPosition().getY()) == 2 && (getPosition().getX()==position.getX())&& !(this.isMoved()))
                || (((position.getX() - this.getPosition().getX() == 1 && position.getY() - this.getPosition().getY() == 1) || (position.getX() - this.getPosition().getX() == -1 && position.getY() - this.getPosition().getY() == 1) ) && canCapture(position, board))) &&
                Check.checkUnderAttack(position, board);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Integer> x = Arrays.asList(1, 0, -1, 0);
        List<Integer> y = Arrays.asList(1, 1, 1, 2);
        List<Position> validMoves = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            validMoves.add(new Position(this.getPosition().getX() + x.get(i), this.getPosition().getY() + y.get(i)));
        }

        return validMoves;
    }


    public boolean canPromote(Position position, Board board) {
        return canMove(position, board) && ((this.getColor() == Color.White && position.getY() == 7)
                || (this.getColor() == Color.Black && position.getY() == 0));
        // TODO: Here or in the front-end, return the list of the pieces the pawn can promote
    }
}
