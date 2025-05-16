package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.services.check.CheckService;

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
    public String getName() {
        return "Pawn";
    }

    @Override
    public boolean canMove(Position position, Board board) {
        return  checkBoarder(position, board)
                && (
                   ((position.getY() - this.getPosition().getY()) == ((this.getColor() == Color.White) ? 1 : -1) && (getPosition().getX()==position.getX()) && !board.getChessBoard()[position.getX()][position.getY()].getIsFilled())
                || ((position.getY() - this.getPosition().getY()) == ((this.getColor() == Color.White) ? 2 : -2) && (getPosition().getX()==position.getX()) && !(this.isMoved())) && !board.getChessBoard()[position.getX()][position.getY()].getIsFilled()
                || (((position.getX() - this.getPosition().getX() == 1 && position.getY() - this.getPosition().getY() == (this.getColor() == Color.White ? 1 : -1)) || (position.getX() - this.getPosition().getX() == -1 && position.getY() - this.getPosition().getY() == (this.getColor() == Color.White ? 1 : -1)) ) && canCapture(position, board))
                ) &&
                CheckService.safeKing(this.getPosition(), position, board);
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        int checkBlack = this.getColor() == Color.Black ? -1 : 1;
        List<Integer> x = Arrays.asList(1, 0, -1, 0);
        List<Integer> y = Arrays.asList(checkBlack, checkBlack, checkBlack, 2 * checkBlack);
        List<Position> validMoves = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            validMoves.add(new Position(this.getPosition().getX() + x.get(i), this.getPosition().getY() + y.get(i)));
        }

        return validMoves;
    }


    public Piece getPromotionPiece(String promotionPiece) {
        if (promotionPiece.charAt(1) == 'Q'){
            return new Queen(this.getColor(), this.getPosition());
        } else if (promotionPiece.charAt(1) == 'N'){
            return new Knight(this.getColor(), this.getPosition());
        } else if (promotionPiece.charAt(1) == 'R'){
            return new Rook(this.getColor(), this.getPosition());
        } else if (promotionPiece.charAt(1) == 'B'){
            return new Bishop(this.getColor(), this.getPosition());
        } else {
            throw new RuntimeException("Invalid piece");
        }
    }
}
