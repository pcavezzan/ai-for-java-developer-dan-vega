package fr.ctw.spring.boot.ai.springbootiaworkshop.memory

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemoryController(
    private val chatClient: ChatClient
) {

    @Autowired
    constructor(
        chatClientBuilder: ChatClient.Builder,
        chatMemory: ChatMemory
    ) : this(
        chatClient = chatClientBuilder
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
            .build(),
    )

    @GetMapping("/memory")
    fun memory(
        @RequestParam(required = true) message: String
    ): String? =
        chatClient
            .prompt()
            .user(message)
            .call()
            .content()
}