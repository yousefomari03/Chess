package com.example.Chess.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GameOverDTO {
    private boolean over;
    private String reason;
    private ClientDTO winner;
}
