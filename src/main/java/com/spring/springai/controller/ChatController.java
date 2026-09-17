package com.spring.springai.controller;


import com.spring.springai.enums.ChatTypeEnum;
import com.spring.springai.service.IChatHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;

    private  final IChatHistory chatHistory;

    @RequestMapping("/chat")
    public Flux<String> chat(@RequestParam String msg, @RequestParam String chatId) {
        // 保存会话记录(会话id 和类型)
        chatHistory.save(ChatTypeEnum.CHAT.getType(), chatId);
        return chatClient.prompt()
                .user(msg)
                .advisors(a -> a.param("CHAT_MEMORY_CONVERSATION_ID", chatId))
                .stream()
                .content();
    }
}
