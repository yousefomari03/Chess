package com.example.Chess.services.check;

import com.example.Chess.board.Board;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Check {
    public boolean checkUnderAttack (ArrayList<Piece> pieces, Position position, Board board){
        for (Piece piece: pieces){
            if (piece.canMove(position,board)){
                return true;
            }

        }
        return false;
    }
    public boolean checkmate (ArrayList<Piece> pieces, King king, Board board){
        List<Integer> x= Arrays.asList(1,-1,0,0,1,-1,-1,1);
        List<Integer> y= Arrays.asList(0,0,1,-1,1,-1,1,-1);
        for(int i=0;i<8 ; i++){
            Position p=new Position(king.getPosition().getX()+x.get(i),king.getPosition().getY()+y.get(i));


            if(king.canMove(p,board) && !checkUnderAttack(pieces,p,board)){
                return false;

            }

        }
        return true;
        //TODO:return positinos
        //TODO:can save the king or can capture

    }


}
