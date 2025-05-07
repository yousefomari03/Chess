package com.example.Chess.board;

import com.example.Chess.model.Client;
import com.example.Chess.pieces.King;
import com.example.Chess.pieces.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Board {
    private int row;
    private int col;
    Cell[][] chessBoard;
    protected ArrayList<Client> players;
    private int currentPlayer;

    public Board(int row, int col) {
        this.row = row;
        this.col = col;
        chessBoard = new Cell[row][col];
    }

    public void fillChessBoard() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                chessBoard[i][j]=new Cell((char)('A' + j)+""+(i + 1),false);

            }

        }
    }

    public boolean isCellEmpty(Position position) {
        return !this.getChessBoard()[position.getX()][position.getY()].getIsFilled();
    }


}
