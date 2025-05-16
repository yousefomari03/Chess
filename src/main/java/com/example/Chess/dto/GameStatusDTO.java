package com.example.Chess.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GameStatusDTO {
    private String fen;
    private boolean gameOver;
    private ClientDTO winner;
    private int currentTurn;
    private int numOfPlayers;
    private int whiteTimeLeft;
    private int blackTimeLeft;
    private int increments;
    private boolean started;
    private String gameOverReason;
}
