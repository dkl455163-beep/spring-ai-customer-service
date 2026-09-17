package com.spring.springai.service;


import com.spring.springai.service.impl.IChatHistoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface IChatHistory {
    /**
     * 保存会话记录
     * @param type 业务类型
     * @param chatId 会话ID
     */
    void save(String type, String chatId);

    /**
     * 根据类型获取所有会话ID
     * @param type 业务类型
     * @return chatId列表
     */
    List<String> get(String type);

    /**
     * 根据类型和会话ID获取所有消息
     * @param chatId 会话ID
     * @param limit 返回消息数量
     * @return 消息列表
     */
    List<Map<String, String>> getMessages(String chatId, int limit);
}
