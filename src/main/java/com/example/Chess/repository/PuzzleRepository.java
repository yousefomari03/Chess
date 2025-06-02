package com.example.Chess.repository;

import com.example.Chess.model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, String> {
    @Query("SELECT p FROM Puzzle p ORDER BY RANDOM() LIMIT 1")
    Puzzle findRandomPuzzle();

    List<Puzzle> findByThemesContaining(String theme);

    List<Puzzle> findByRatingBetween(int minRating, int maxRating);

    List<Puzzle> findByOpeningContainingIgnoreCase(String opening);
}
