package com.example.Chess.services;

import com.example.Chess.board.Board;
import com.example.Chess.model.Client;
import com.example.Chess.pieces.Piece;

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
}
