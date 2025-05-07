package com.example.Chess.pieces;

import com.example.Chess.board.Board;
import com.example.Chess.enums.Color;
import com.example.Chess.model.Client;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter

public class King extends Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public King(Color color, Position position) {
        super(color, position);
    }
    public King(Position position) {
        super(position);
    }

    public King() {
        super();
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        return List.of();
    }


    @Override
    public boolean canMove(Position position, Board board) {
        return (new Queen(this.getPosition()).canMove(position, board)) &&
                Math.abs(position.getX() - this.getPosition().getX()) <= 1 && Math.abs(position.getY() - this.getPosition().getY()) <= 1;
    }
}
