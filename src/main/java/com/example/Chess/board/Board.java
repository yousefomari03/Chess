package com.example.Chess.board;

import com.example.Chess.pieces.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    Cell[][] chessBoard  = new Cell[8][8];

    public void fillChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j]=new Cell((char)('A' + j)+""+(i + 1),false);

            }

        }
    }

    public boolean isCellEmpty(Position position) {
        return !this.getChessBoard()[position.getX()][position.getY()].getIsFilled();
    }


}
