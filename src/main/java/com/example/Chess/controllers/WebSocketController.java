package com.example.Chess.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/game/change")
    public void handleCodeChange(Map<String, Object> body)  {
        String gameId = body.get("gameId").toString();
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("gameStatus", body.get("gameStatus"));
        resBody.put("token", body.get("token").toString());
        messagingTemplate.convertAndSend("/topic/game/" + gameId, resBody);
    }
}