package com.example.Chess.board;

import com.example.Chess.enums.Color;
import com.example.Chess.model.Client;
import com.example.Chess.pieces.Piece;
import com.example.Chess.pieces.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Board implements Serializable {
    private int row;
    private int col;
    private String fen;
    private boolean filled;
    private boolean started;
    private int secondsPerSide;
    private int currentPlayer;
    private Cell[][] chessBoard;
    private ArrayList<Client> players;
    private ArrayList<Integer> playerPassedTime;
    private ArrayList<Long> playerLastMoveTimes;

    public Board(int row, int col, ArrayList<Client> players) {
        this.row = row;
        this.col = col;
        this.players = players;
        this.currentPlayer = 0;
        this.started = false;
        chessBoard = new Cell[row][col];
        secondsPerSide = 180;

        playerLastMoveTimes = new ArrayList<>();
        playerLastMoveTimes.add(Long.MAX_VALUE);
        playerLastMoveTimes.add(Long.MAX_VALUE);

        playerPassedTime = new ArrayList<>();
        playerPassedTime.add(0);
        playerPassedTime.add(0);
        fillBoard();
    }

    public void fillBoard() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                chessBoard[i][j]=new Cell((char)('a' + i)+""+(j + 1),false);
            }
        }
    }

    public boolean isCellEmpty(Position position) {
        return !getChessBoard()[position.getX()][position.getY()].getIsFilled();
    }

    public String getFen(){
        String fen = "";
        for (int i = row - 1; i >= 0; i--) {
            int c = 0;
            for (int j = 0; j < col; j++) {
                if (chessBoard[j][i].getPiece() == null){
                    c++;
                    continue;
                }
                if (c != 0){
                    fen += String.valueOf(c);
                }
                String name = chessBoard[j][i].getPiece().getName();
                String first = String.valueOf(name.toLowerCase().charAt(0));
                if (name.equals("Knight")){
                    first = "n";
                }

                if (chessBoard[j][i].getPiece().getColor() == Color.White){
                    first = first.toUpperCase();
                }

                fen += first;
                c = 0;
            }
            if (c!=0){
                fen += String.valueOf(c);
            }
            if (i > 0)
                fen += "/";
        }

        return fen;
    }
}
// TODO: implement increments per move (when moves report is ready)