package com.example.Chess.services.check;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import com.example.Chess.services.PiecesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Check {
    public static boolean checkUnderAttack (ArrayList<Piece> pieces, Position position, Board board){
        for (Piece piece: pieces){
            if (piece.canMove(position,board)){
                return true;
            }

        }
        return false;
    }

    public static boolean checkmate (ArrayList<Piece> pieces, King king, Board board){
        List<Integer> x= Arrays.asList(1,-1,0,0,1,-1,-1,1);
        List<Integer> y= Arrays.asList(0,0,1,-1,1,-1,1,-1);
        for(int i=0;i<8 ; i++){
            Position p=new Position(king.getPosition().getX()+x.get(i),king.getPosition().getY()+y.get(i));

            if(king.canMove(p,board) && !checkUnderAttack(pieces,p,board)){
                return false;

            }
        }

        return !canProtect(PiecesService.getPieces(board.getPlayers().get(board.getCurrentPlayer()), board), king, board);
        //TODO:return positinos
    }

    public static boolean checkUnderAttack(Position position, Board board){
        Cell cell = board.getChessBoard()[position.getX()][position.getY()];
        Piece piece = cell.getPiece();
        if (piece != null) {
            cell.setPiece(null);
        }
        boolean can = checkUnderAttack(PiecesService.getPieces(board.getPlayers().get((board.getCurrentPlayer() + 1) % 2), board), board.getPlayers().get((board.getCurrentPlayer() + 1) % 2).getKing().getPosition(), board);
        cell.setPiece(piece);

        return can;
    } // TODO: change name

    public static boolean canProtect(ArrayList<Piece> pieces, King king, Board board){
        for(Piece piece: pieces){
            List<Position> positions = piece.getValidMoves(board);
            for(Position position: positions){
                if (piece.canMove(position,board)){
                    return true;
                }
            }
        }

        return false;
    }
}
