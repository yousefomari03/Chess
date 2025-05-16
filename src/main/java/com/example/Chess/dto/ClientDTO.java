package com.example.Chess.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private int turn;
}
