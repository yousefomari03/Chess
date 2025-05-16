package com.example.Chess.moves;

import com.example.Chess.board.Board;
import com.example.Chess.model.CastlingPositions;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.pieces.Rook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Castling implements Move {
    private King king;
    public Castling(King King) {
        this.king = King;
    }
    @Override

    public boolean canMove(Position position, Board board) {
        if (!isCastling(position, board)) {
            return false;
        }

        Piece rook = board.getChessBoard()[position.getX()][position.getY()].getPiece();
        return !king.isMoved() && !rook.isMoved() && rook.canPass(king.getPosition(), board);
    }

    public boolean isCastling(Position position, Board board) {
        Piece piece = board.getChessBoard()[position.getX()][position.getY()].getPiece();
        return piece instanceof Rook && king.getColor() == piece.getColor();
    }

    public CastlingPositions getCastlingPositions(King king, Rook rook) {
        int n = rook.getPosition().getX()-king.getPosition().getX();
        n = n < 0 ? -2 : 2;

        Position kingPosition = new Position(king.getPosition().getX() + n, king.getPosition().getY());
        Position rookPosition = new Position(kingPosition.getX() + ((n > 0) ? -1 : 1), rook.getPosition().getY());
        return new CastlingPositions(kingPosition, rookPosition);
    }
}
