package com.example.Chess.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDService {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
