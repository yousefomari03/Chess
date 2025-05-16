package com.example.Chess.model;

import com.example.Chess.pieces.Position;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CastlingPositions {
    private Position kingPosition;
    private Position rookPosition;
}
