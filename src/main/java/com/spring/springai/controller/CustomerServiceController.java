package com.spring.springai.controller;

import com.spring.springai.enums.ChatTypeEnum;
import com.spring.springai.service.IChatHistory;
import com.spring.springai.tools.CourseTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class CustomerServiceController {
    private  final ChatClient serviceChatClient;
    private  final IChatHistory chatHistory;
    @RequestMapping("/service")
    public String service(@RequestParam String msg, @RequestParam String chatId) {
        // 添加会话记录
        chatHistory.save(ChatTypeEnum.SERVICE.getType(), chatId);
        return serviceChatClient.prompt()
                .user(msg)
                .advisors(a -> a.param("CHAT_MEMORY_CONVERSATION_ID", chatId))
                .call()
                .content();
    }

}
