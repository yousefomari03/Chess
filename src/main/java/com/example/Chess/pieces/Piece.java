package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.enums.Color;
import com.example.Chess.moves.Move;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

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

    public boolean checkBoarder(Position position, Board board) {
        return position.getX() < board.getChessBoard().length && position.getY() < board.getChessBoard()[0].length;
    }

    public boolean canPass(Position position, Board board) {
        int x = this.position.getX();
        int y = this.position.getY();
        int x1 = position.getX();
        int y1 = position.getY();

        int a1 = x1 - x;
        if (a1 != 0){
            a1 /= Math.abs(a1);
        }
        int a2 = y1 - y;
        if (a2 != 0){
            a2 /= Math.abs(a2);
        }
        for(int i = x + a1, j = y + a2; i != x1 && j != y1 ; i += a1, j += a2){
            if (board.getChessBoard()[i][j].getIsFilled()){
                return false;
            }
        }
        if (board.isCellEmpty(position)){
            return true;
        }

        return canCapture(position, board);
    }

    public boolean canCapture(Position position, Board board) {
        Cell cell = board.getChessBoard()[position.getX()][position.getY()];
        if (cell.getPiece() != null){
            return cell.getPiece().getColor() != this.getColor();
            //TODO: handle capture;
        }

        return false;
    }

}
