package com.example.Chess.rules.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.pieces.Rook;
import com.example.Chess.services.PiecesService;

public class FenPiecesSetter extends PiecesSetter {
    public FenPiecesSetter(String fen) {
        this.setFen(fen);
    }

    @Override
    public void setPieces(Board board) {
        String[] fenData = this.getFen().split(" ");
        String[] fen = fenData[0].split("/");
        String castlingRights = fenData[2];
        for(int i = fen.length - 1; i >= 0; i--) {
            int col = 0;
            for (int j = 0; j < fen[i].length(); j++) {
                if (Character.isDigit(fen[i].charAt(j))){
                    int digit = Integer.parseInt(fen[i].charAt(j) + "");
                    col += digit;
                    continue;
                }
                Position position = new Position(col, fen.length - 1 - i);
                Piece piece = PiecesService.getPieceFromChar(fen[i].charAt(j), position);

                if (piece instanceof King king){
                    if (king.getColor() == Color.White){
                        board.getPlayers().get(0).setKing(king);
                        board.getPlayers().get(0).setColor(Color.White);
                    } else {
                        board.getPlayers().get(1).setKing(king);
                        board.getPlayers().get(1).setColor(Color.Black);
                    }
                }

                if (piece instanceof Rook){
                    if (piece.getColor() == Color.White){
                        if (!((position.getX() == 0 || position.getX() == 7) && position.getY() == 0)){
                            piece.setMoved(true);
                        } else {
                            if (position.getX() == 0 && !castlingRights.contains("Q")) {
                                piece.setMoved(true);
                            }

                            if (position.getX() == 7 && !castlingRights.contains("K")) {
                                piece.setMoved(true);
                            }
                        }
                    } else {
                        if (!((position.getX() == 0 || position.getX() == 7) && position.getY() == 7)){
                            piece.setMoved(true);
                        } else {
                            if (position.getX() == 0 && !castlingRights.contains("q")) {
                                piece.setMoved(true);
                            }

                            if (position.getX() == 7 && !castlingRights.contains("k")) {
                                piece.setMoved(true);
                            }
                        }
                    }
                }
                board.getChessBoard()[col][fen.length - 1 - i].setPiece(piece);
                col++;
            }
        }
        board.setCurrentPlayer(fenData[1].equals("w") ? 0 : 1);
        board.setStarted(!fenData[0].equals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));

        board.setFen(board.getFen());
        board.setFilled(true);
    }
}
