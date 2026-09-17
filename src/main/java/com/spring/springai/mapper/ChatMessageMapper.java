package com.spring.springai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.springai.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
