package com.spring.springai.cofig;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.springai.entity.ChatMessage;
import com.spring.springai.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MysqlChatMemory implements ChatMemory {

    private final ChatMessageMapper chatMessageMapper;

    @Override
    public List<Message> get(String conversationId) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getChatId, conversationId);
        wrapper.orderByAsc(ChatMessage::getId);

        List<ChatMessage> dbMessages = chatMessageMapper.selectList(wrapper);
        List<Message> messages = new ArrayList<>();

        for (ChatMessage msg : dbMessages) {
            if ("user".equals(msg.getRole())) {
                messages.add(new UserMessage(msg.getContent()));
            } else {
                messages.add(new AssistantMessage(msg.getContent()));
            }
        }
        return messages;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        for (Message message : messages) {
            ChatMessage msg = new ChatMessage();
            msg.setChatId(conversationId);
            msg.setRole(message.getMessageType().name().toLowerCase());
            msg.setContent(message.getText());
            chatMessageMapper.insert(msg);
        }
    }

    @Override
    public void clear(@Nullable String conversationId) {

    }
}