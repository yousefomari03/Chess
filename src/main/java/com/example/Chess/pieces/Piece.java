package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.enums.Color;
import com.example.Chess.moves.Move;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

public abstract class Piece implements Move {
    private Long id;
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

    public Piece(Long id) {
        this.id = id;
    }


    public abstract List<Position> getValidMoves(Board board);

    public List<Position> getPositionsWhileValid(Board board, List<Integer> x, List<Integer> y){
        List<Position> validMoves = new ArrayList<>();


        for(int i = 0; i < 4; i++){
            Position position = new Position(this.getPosition().getX(), this.getPosition().getY());
            while(position.getX()<board.getRow() && position.getY()<board.getCol()){
                position.setX(position.getX() + x.get(i));
                position.setY(position.getY() + y.get(i));
                validMoves.add(position);
            }
        }

        return validMoves;

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
       return true;
    }

    public boolean canCapture(Position position, Board board) {
        Cell cell = board.getChessBoard()[position.getX()][position.getY()];
        if (cell.getPiece() != null){
            if (cell.getPiece().getColor() != this.getColor()){
//                cell.setPiece(null);
//                cell.setIsFilled(false);
                return true;
            }
            return false;
        }

        return false;
    }
    public boolean canStep(Position position, Board board) {
        if (canPass(position, board)){
            if (board.isCellEmpty(position)){
                return true;
            }

            return canCapture(position, board);


        }
        return false;
    }

}
