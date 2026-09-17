package com.spring.springai.enums;


import lombok.Getter;

@Getter
public enum ChatTypeEnum {

        CHAT("chat"),     // 普通聊天
        SERVICE("service"), // 客服
        PDF("pdf");       // PDF文档问答

        private final String type;

        ChatTypeEnum(String type) {
            this.type = type;
        }
}
