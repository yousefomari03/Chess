package com.example.Chess.rules.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.enums.Color;
import com.example.Chess.pieces.*;

import java.util.ArrayList;

public class NormalPiecesSetter implements PiecesSetter {

    @Override
    public void setPiece(Board board) {
        for(int i = 0; i < 4; i++){
            board.getChessBoard()[i][1] = new Cell(true, new Pawn(Color.White, new Position(i, 1)));
            board.getChessBoard()[i][6] = new Cell(true, new Pawn(Color.Black, new Position(i, 6)));
            board.getChessBoard()[i + (7 - 2 * (i))][6] = new Cell(true, new Pawn(Color.Black, new Position(i, 6)));
            board.getChessBoard()[i + (7 - 2 * (i))][1] = new Cell(true, new Pawn(Color.White, new Position(i, 1)));
        }

        board.getChessBoard()[0][0] = new Cell(true, new Rook(Color.White, new Position(0, 0)));
        board.getChessBoard()[0][7] = new Cell(true, new Rook(Color.Black, new Position(0, 7)));
        board.getChessBoard()[7][7] = new Cell(true, new Rook(Color.Black, new Position(7, 7)));
        board.getChessBoard()[7][0] = new Cell(true, new Rook(Color.White, new Position(7, 0)));

        board.getChessBoard()[1][0] = new Cell(true, new Knight(Color.White, new Position(1, 0)));
        board.getChessBoard()[1][7] = new Cell(true, new Knight(Color.Black, new Position(1, 7)));
        board.getChessBoard()[6][7] = new Cell(true, new Knight(Color.Black, new Position(6, 7)));
        board.getChessBoard()[6][0] = new Cell(true, new Knight(Color.White, new Position(6, 0)));

        board.getChessBoard()[2][0] = new Cell(true, new Bishop(Color.White, new Position(2, 0)));
        board.getChessBoard()[2][7] = new Cell(true, new Bishop(Color.Black, new Position(2, 7)));
        board.getChessBoard()[5][7] = new Cell(true, new Bishop(Color.Black, new Position(5, 7)));
        board.getChessBoard()[5][0] = new Cell(true, new Bishop(Color.White, new Position(5, 0)));

        board.getChessBoard()[3][0] = new Cell(true, new Queen(Color.White, new Position(3, 0)));
        board.getChessBoard()[3][7] = new Cell(true, new Queen(Color.Black, new Position(3, 7)));

        King whiteKing = new King(Color.White, new Position(4, 0));
        board.getChessBoard()[4][0] = new Cell(true, whiteKing);
        board.getPlayers().get(0).setKing(whiteKing);

        King blackKing = new King(Color.Black, new Position(4, 7));
        board.getChessBoard()[4][7] = new Cell(true, blackKing);
        board.getPlayers().get(1).setKing(blackKing);
    }
}