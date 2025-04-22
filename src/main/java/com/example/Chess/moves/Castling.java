package com.example.Chess.moves;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.pieces.Rook;

public class Castling implements Move {
    private King king;
    public Castling(King King) {
        this.king = King;
    }
    @Override

    public boolean canMove(Position position, Board board) {
        Piece rook = board.getChessBoard()[position.getX()][position.getY()].getPiece();
        if(king.isMoved()||rook.isMoved()){
            return false;
        }
        if(!rook.canPass(king.getPosition(), board)){
            return false;

        }
        //TODO:check is ander attack
        return true;
    }

    public void castle(King king,Rook rook, Board board) {
        int n = rook.getPosition().getX()-king.getPosition().getX();
        if(n<0){
            n=-2;
        }
        else {
            n = 2;
        }
        king.getPosition().setX(king.getPosition().getX()+n);
        Cell cell = board.getChessBoard()[king.getPosition().getX()][king.getPosition().getY()];
        cell.setPiece(king);
        cell.setIsFilled(true);
        king.setMoved(true);

        rook.getPosition().setX(king.getPosition().getX() + ((n > 0) ? -1 : 1));
        cell = board.getChessBoard()[rook.getPosition().getX()][rook.getPosition().getY()];
        cell.setPiece(rook);
        cell.setIsFilled(true);
        rook.setMoved(true);
    }
}
