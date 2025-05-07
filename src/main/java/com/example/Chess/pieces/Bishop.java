package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.services.check.Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Position position) {
        super(color, position);
    }
    public Bishop(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return (checkBoarder(position, board) && Math.abs(getPosition().getX()-position.getX())==Math.abs(getPosition().getY()-position.getY())
                && Math.abs(getPosition().getY()-position.getY())!=0 && canStep(position, board)) && Check.checkUnderAttack(position, board);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Integer> x = Arrays.asList(1,-1,-1,1);
        List<Integer> y = Arrays.asList(1,-1,1,-1);
        return this.getPositionsWhileValid(board, x, y);
    }
}
