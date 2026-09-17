package com.spring.springai.controller;


import com.spring.springai.enums.ChatTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RequestMapping("/ai")
@RestController
public class GameController {

    private final ChatClient gameClient;
    @RequestMapping("/game")
    public Flux<String> chat(@RequestParam String msg, @RequestParam String chatId) {
        return gameClient.prompt()
                .user(msg)
                .advisors(a -> a.param("CHAT_MEMORY_CONVERSATION_ID", chatId))
                .stream()
                .content();
    }

}
