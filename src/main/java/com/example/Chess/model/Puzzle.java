package com.example.Chess.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Puzzle implements Serializable {
    @Id
    private String id;

    @Column
    private String fen;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "puzzle_solutions", joinColumns = @JoinColumn(name = "puzzle_id"))
    @Column(name = "move")
    private List<String> solutionMoves;

    private int rating;
    private int plays;
    private int successRate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "puzzle_themes", joinColumns = @JoinColumn(name = "puzzle_id"))
    @Column(name = "theme")
    private List<String> themes;

    private String opening;
    private int currentMove;
}