package com.spring.springai.cofig;

import com.openai.models.vectorstores.VectorStore;
import com.spring.springai.tools.CourseTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class cofiguration {

    private  final MysqlChatMemory chatMemory;
    /*
    * 创建聊天客户端
    * */
    @Bean(name = "chatClient")
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("你是小龙女")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    /*
    * 创建游戏聊天客户端
    * */
    @Bean(name = "gameChatClient")
    public ChatClient gameChatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(SystemConstens.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }


    /*
    * 创建服务聊天客户端
    * */
    @Bean(name = "serviceChatClient")
    public ChatClient serviceChatClient(ChatModel chatModel , CourseTools courseTools) {
        return ChatClient.builder(chatModel)
                .defaultSystem(SystemConstens.SERVICE_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultTools(courseTools)
                .build();

    }
}