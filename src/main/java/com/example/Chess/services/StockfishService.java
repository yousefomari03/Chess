package com.example.Chess.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class StockfishService {
    private Process stockfish;
    private BufferedReader reader;
    private BufferedWriter writer;

    @PostConstruct
    public void startEngine() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\ahmad\\OneDrive\\Desktop\\Capstone\\Chess\\src\\main\\resources\\stockfish\\stockfish-windows-x86-64-avx2.exe");
        stockfish = pb.start();
        reader = new BufferedReader(new InputStreamReader(stockfish.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(stockfish.getOutputStream()));
    }

    public String getBestMove(String fen, int depth) {
        sendCommand("uci");
        sendCommand("isready");

        sendCommand("position fen " + fen);
        sendCommand("go depth " + depth);

        String bestMove = null;
        try{
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("bestmove")) {
                    bestMove = line.split(" ")[1];
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bestMove;
    }

    private void sendCommand(String command) {
        try{
            writer.write(command + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void close() throws IOException {
        sendCommand("quit");
        reader.close();
        writer.close();
        stockfish.destroy();
    }
}
