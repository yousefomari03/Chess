package com.example.Chess.services.check;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.enums.Color;
import com.example.Chess.model.Client;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.services.PiecesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckService {
    public static boolean kingUnderAttack(ArrayList<Piece> oppPieces, Position position, Board board){
        for (Piece piece: oppPieces){
            if (piece.canMove(position,board)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkmate(int currentPlayer, Board board){
        King king = board.getPlayers().get(currentPlayer).getKing();
        ArrayList<Piece> pieces = PiecesService.getPieces(board.getPlayers().get((currentPlayer + 1) % 2), board);
        List<Integer> x= Arrays.asList(1,-1,0,0,1,-1,-1,1);
        List<Integer> y= Arrays.asList(0,0,1,-1,1,-1,1,-1);
        for(int i=0;i<8 ; i++){
            Position p=new Position(king.getPosition().getX()+x.get(i),king.getPosition().getY()+y.get(i));

            if(king.canMove(p,board) && !kingUnderAttack(pieces, p, board)){
                return false;
            }
        }

        return !canProtect(PiecesService.getPieces(board.getPlayers().get(currentPlayer), board), board);
    }

    public static boolean safeKing(Position src, Position dist, Board board){
        Cell srcCell = board.getChessBoard()[src.getX()][src.getY()];
        Piece srcPiece = srcCell.getPiece();
        int currentPlayer = (srcPiece.getColor() == Color.White) ? 0 : 1;

        srcCell.setPiece(null);
        srcCell.setIsFilled(false);

        Cell distCell = board.getChessBoard()[dist.getX()][dist.getY()];
        Piece distPiece = distCell.getPiece();
        boolean distFilled = distCell.getIsFilled();
        distCell.setPiece(srcPiece);
        distCell.setIsFilled(true);
        srcPiece.setPosition(dist);
        ArrayList<Piece> oppPieces = PiecesService.getPieces(board.getPlayers().get((currentPlayer + 1) % 2), board);
        boolean can = kingUnderAttack(
                oppPieces
                ,board.getPlayers().get(currentPlayer).getKing().getPosition(), board);

        srcCell.setPiece(srcPiece);
        srcCell.setIsFilled(true);
        srcPiece.setPosition(src);

        distCell.setPiece(distPiece);
        distCell.setIsFilled(distFilled);

        return !can;
    }

    public static boolean canProtect(ArrayList<Piece> pieces, Board board){
        for(Piece piece: pieces){
            System.out.println(piece.getColor() + " " + piece.getName() + " " + piece.getPosition());
            List<Position> positions = piece.getValidMoves(board);
            for(Position position: positions){
                System.out.println("Can move to " + position);
                if (piece.canMove(position,board)){
                    return true;
                }
            }
        }

        return false;
    }
}
