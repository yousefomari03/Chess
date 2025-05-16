package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Position position) {
        super(color, position);
    }
    public Queen(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Queen";
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return (
                new Bishop(this.getColor(), this.getPosition()).canMove(position, board)
               || new Rook(this.getColor(), this.getPosition()).canMove(position, board));
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        Rook rook = new Rook(this.getColor(), this.getPosition());
        Bishop bishop = new Bishop(this.getColor(), this.getPosition());

        List<Position> validMoves = rook.getValidMoves(board);
        validMoves.addAll(bishop.getValidMoves(board));
        return validMoves;
    }
}
