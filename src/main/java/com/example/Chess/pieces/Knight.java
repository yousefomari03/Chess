package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.services.check.Check;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(Color color, Position position) {
        super(color, position);
    }
    public Knight(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return (checkBoarder(position, board) && (Math.abs(getPosition().getX()-position.getX()) ==1 && (Math.abs(getPosition().getY()-position.getY()))==2)
                ||(Math.abs(getPosition().getX()-position.getX())==2 && Math.abs(getPosition().getY()-position.getY())==1)
                && (canCapture(position, board) || board.isCellEmpty(position))) &&
                Check.checkUnderAttack(position, board);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<List<Integer>> coordinates = List.of(List.of(-1, 1), List.of(-2, 2));
        List<Position> validMoves = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 2; k++){
                    Position position = new Position(coordinates.get(i).get(j), coordinates.get((i + 1) % 2).get(k));
                    validMoves.add(position);
                }
            }
        }

        return validMoves;
    }

}
