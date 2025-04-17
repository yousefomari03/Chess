package com.example.Chess.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expired=" + expired +
                ", revoked=" + revoked +
                '}';
    }
}
