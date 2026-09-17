package com.spring.springai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.springai.entity.ChatHistory;
import com.spring.springai.entity.ChatMessage;
import com.spring.springai.mapper.ChatHistoryMapper;
import com.spring.springai.mapper.ChatMessageMapper;
import com.spring.springai.service.IChatHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IChatHistoryImpl  implements IChatHistory {

    private final ChatHistoryMapper chatHistoryMapper;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public void save(String type, String chatId) {
            // 先判断是否已存在，避免重复插入
            LambdaQueryWrapper<ChatHistory> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ChatHistory::getType, type)
                    .eq(ChatHistory::getChatId, chatId);

            Long count = chatHistoryMapper.selectCount(wrapper);
            // 不存在才新增
            if (count == 0) {
                ChatHistory chatHistory = new ChatHistory();
                chatHistory.setType(type);
                chatHistory.setChatId(chatId);
                chatHistoryMapper.insert(chatHistory);
            }
    }

    @Override
    public List<String> get(String type) {
        if (type == null) {
            return List.of();
        }
        // MyBatis-Plus 查询
        LambdaQueryWrapper<ChatHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatHistory::getType, type);

        List<ChatHistory> list = chatHistoryMapper.selectList(wrapper);

        // 提取 chatId 列表
        return list.stream()
                .map(ChatHistory::getChatId)
                .collect(Collectors.toList());
    }


    // ====================== 你要的最终版本：id + 条数 ======================
    @Override
    public List<Map<String, String>> getMessages(String chatId, int limit) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getChatId, chatId);
        wrapper.orderByAsc(ChatMessage::getId);

        // 查询所有数据，再限制返回条数（满足你的需求）
        List<ChatMessage> messages = chatMessageMapper.selectList(wrapper);

        // 限制返回条数：取最后 N 条
        int end = messages.size();
        int start = Math.max(0, end - limit);

        return messages.subList(start, end).stream()
                .map(msg -> Map.of(
                        "role", msg.getRole(),
                        "content", msg.getContent()
                ))
                .collect(Collectors.toList());
    }
}
