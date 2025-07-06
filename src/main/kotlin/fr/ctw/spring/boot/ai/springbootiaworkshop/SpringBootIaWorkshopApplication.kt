package fr.ctw.spring.boot.ai.springbootiaworkshop

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringBootIaWorkshopApplication {

    @Bean
    fun openAIChatClient(chatModel: OpenAiChatModel): ChatClient {
        return ChatClient.create(chatModel)
    }

    @Bean
    fun ollamaChatClient(chatModel: OllamaChatModel): ChatClient {
        return ChatClient.create(chatModel)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootIaWorkshopApplication>(*args)
}
