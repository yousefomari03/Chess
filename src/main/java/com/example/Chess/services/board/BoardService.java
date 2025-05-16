package com.example.Chess.services.board;

import com.example.Chess.board.Board;
import com.example.Chess.board.Cell;
import com.example.Chess.pieces.Position;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    public Cell getCellFromPosition(Position position, Board board) {
        return board.getChessBoard()[position.getX()][position.getY()];
    }

}
