package com.example.Chess.services;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.model.Client;
import com.example.Chess.pieces.*;

import java.util.ArrayList;

public class PiecesService {
    public static ArrayList<Piece> getPieces(Client client, Board board) {
        ArrayList<Piece> pieces = new ArrayList<>();
        int row = board.getRow();
        int col = board.getCol();

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                Piece piece = board.getChessBoard()[i][j].getPiece();
                if (piece != null && piece.getColor() == client.getColor()){
                    pieces.add(piece);
                }
            }
        }

        return pieces;
    }

    public static Piece getPieceFromChar(char pieceChar, Position position){
        char ch = Character.toLowerCase(pieceChar);
        Piece piece;
        Color color = Character.isLowerCase(pieceChar) ? Color.Black : Color.White;
        if (ch == 'p'){
            piece = new Pawn(color, position);
        } else if (ch == 'b'){
            piece = new Bishop(color, position);
        } else if (ch == 'n'){
            piece = new Knight(color, position);
        } else if (ch == 'r'){
            piece = new Rook(color, position);
        } else if (ch == 'k'){
            piece = new King(color, position);
        } else if (ch == 'q'){
            piece = new Queen(color, position);
        } else {
            throw new RuntimeException("Invalid piece character");
        }

        return piece;
    }
}
