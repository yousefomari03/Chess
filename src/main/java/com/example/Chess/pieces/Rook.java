package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.services.check.CheckService;

import java.util.Arrays;
import java.util.List;

public class Rook extends Piece {
    public Rook(Color color, Position position) {
        super(color, position);
    }
    public Rook(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Rook";
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return checkBoarder(position, board)
                && (
                        (((getPosition().getX() == position.getX()) || (getPosition().getY()==position.getY())) && !(getPosition().equals(position)))
                        && canStep(position, board)
                ) &&
                CheckService.safeKing(this.getPosition(), position, board);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Integer> x= Arrays.asList(1,-1,0,0);
        List<Integer> y= Arrays.asList(0,0,1,-1);
        return this.getPositionsWhileValid(board, x, y);
    }
}
