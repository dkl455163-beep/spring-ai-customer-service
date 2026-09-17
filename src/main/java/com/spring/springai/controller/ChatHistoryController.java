package com.spring.springai.controller;


import com.spring.springai.service.IChatHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai/history")
@RequiredArgsConstructor
public class ChatHistoryController {

    private  final IChatHistory chatHistory;

    @GetMapping("/{type}")
    public List<String> getChatHistory(@PathVariable("type") String type) {
        return chatHistory.get(type);
    }
    @GetMapping("/{type}/{chatId}")
    public List<Map<String, String>> getChatHistoryMessages(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        return chatHistory.getMessages(chatId,Integer.MAX_VALUE);
    }
}
