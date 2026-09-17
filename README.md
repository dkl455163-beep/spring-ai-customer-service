# spring-ai-customer-service

基于 Spring Boot、Spring AI 和 MySQL 实现的 AI 对话与智能客服项目，主要练习大模型接入、流式响应、工具调用和聊天记忆。

## 技术栈

- Java 17
- Spring Boot 4.0.6
- Spring AI 2.0.0-M5
- MyBatis-Plus
- MySQL
- OpenAI 兼容接口
- 通义千问
- Lombok
- Maven

## 已实现功能

- 普通 AI 流式对话
- 智能客服对话
- MySQL 持久化聊天记录
- 按 `chatId` 区分会话
- 查询会话 ID 和聊天消息
- 课程查询工具
- 校区查询工具
- 课程预约工具
- 系统提示词配置

## 项目结构

```text
src/main/java/com/spring/springai
├─ cofig
│  ├─ MysqlChatMemory.java
│  ├─ SystemConstens.java
│  └─ cofiguration.java
├─ controller
│  ├─ ChatController.java
│  ├─ ChatHistoryController.java
│  └─ CustomerServiceController.java
├─ entity
│  ├─ ChatHistory.java
│  ├─ ChatMessage.java
│  └─ po
├─ mapper
├─ service
└─ tools
   └─ CourseTools.java
```

## 环境要求

- JDK 17
- Maven 3.9+
- MySQL 8+
- 可用的 DashScope 或 OpenAI 兼容 API Key

## 数据库

项目默认连接：

```text
jdbc:mysql://127.0.0.1:3306/spring-ai
```

聊天记录使用以下两张表：

```sql
CREATE TABLE chat_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(32) NOT NULL,
    chat_id VARCHAR(64) NOT NULL
);

CREATE TABLE chat_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    chat_id VARCHAR(64) NOT NULL,
    role VARCHAR(32) NOT NULL,
    content TEXT NOT NULL
);
```

`/ai/service` 中的课程查询和课程预约功能还需要以下表：

- `course`
- `school`
- `course_reservation`

需要根据自己的数据库结构创建，或直接使用已有的表结构。

## 配置

修改文件：

```text
src/main/resources/application.yaml
```

数据库和 API Key 使用环境变量：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/spring-ai?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8
    username: ${MYSQL_USERNAME:root}
    password: ${MYSQL_PASSWORD}

  ai:
    openai:
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1
      model: qwen3.5-omni-plus
      api-key: ${OPENAI_API_KEY}
      embedding:
        options:
          model: text-embedding-v3
          dimensions: 1024
```

### 环境变量

在 IDEA 的 Run Configuration 中配置：

```text
MYSQL_USERNAME=root
MYSQL_PASSWORD=你的 MySQL 密码
OPENAI_API_KEY=你的 DashScope 或 OpenAI API Key
```

也可以使用 PowerShell 临时设置：

```powershell
$env:MYSQL_USERNAME="root"
$env:MYSQL_PASSWORD="你的 MySQL 密码"
$env:OPENAI_API_KEY="你的 API Key"
```

不要把真实密码或 API Key 写进 `application.yaml`，也不要提交到 Git。

## 启动项目

方式一，使用 Maven Wrapper：

```powershell
.\mvnw.cmd spring-boot:run
```

方式二，先编译再运行：

```powershell
.\mvnw.cmd clean package
java -jar target\Spring-ai-0.0.1-SNAPSHOT.jar
```

也可以通过 IDEA 运行：

```text
com.spring.springai.SpringAiApplication
```

## 接口说明

### 普通 AI 对话

```http
GET /ai/chat?msg=你好&chatId=1001
```

返回流式响应。`chatId` 用于区分不同会话。

### 智能客服

```http
GET /ai/service?msg=我想了解 Java 课程&chatId=1002
```

客服会根据自己的系统提示词决定是否调用课程、校区或预约工具。

### 查询某类会话 ID

```http
GET /ai/history/chat
GET /ai/history/service
```

### 查询聊天消息

```http
GET /ai/history/service/1002
```

返回该会话中保存的用户消息和 AI 回复。

## 聊天记忆

项目实现了 Spring AI 的 `ChatMemory` 接口，并通过 MyBatis-Plus 将聊天记录写入 MySQL。

- `chat_history`：保存业务类型和 `chatId`
- `chat_message`：保存用户消息和 AI 回复
- `conversationId` 使用 `chatId`，避免不同会话之间互相影响

## 安全说明

以下内容不要提交到 GitHub：

```text
target/
.idea/
*.iml
.env
application-local.yml
application-local.yaml
application-local.properties
```

提交前可以检查 Git 暂存区：

```powershell
git grep -n -i -E "sk-[A-Za-z0-9_-]{8,}|password:\s*[^$]|accessKey(Id|Secret):\s*[^$]" --cached
```

## 项目说明

本项目属于个人学习实践，重点练习 Spring AI 对话、MySQL 聊天记忆、系统提示词和工具调用。项目中的课程与校区数据需要在本地数据库中自行准备。
