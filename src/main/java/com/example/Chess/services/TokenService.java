package com.example.Chess.services;

import com.example.Chess.model.Token;
import com.example.Chess.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public List<Token> findAllValidTokenClient(Long id) {
        return tokenRepository.findAllValidTokenClient(id);
    }

    public void deleteAll(List<Token> tokens) {
        tokenRepository.deleteAll(tokens);
    }
}
