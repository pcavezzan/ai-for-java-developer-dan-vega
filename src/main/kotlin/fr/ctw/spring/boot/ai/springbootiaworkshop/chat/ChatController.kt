package fr.ctw.spring.boot.ai.springbootiaworkshop.chat

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ChatController(private val chatClient: ChatClient) {

    @Autowired
    constructor(chatClientBuilder: ChatClient.Builder) : this(chatClient = chatClientBuilder.build())

    @GetMapping("/chat")
    fun chat(): String? =
        chatClient
            .prompt("Tell me interesting facts about Java")
            .call()
            .content()

    @GetMapping("/stream")
    fun stream(): Flux<String> =
        chatClient
            .prompt()
            .user("I'm visiting Hilton Head soon, can you give me 10 places to visit?")
            .stream()
            .content()

    @GetMapping("/joke")
    fun joke(): ChatResponse? =
        chatClient.prompt()
            .user("Tell me a dad joke about dogs")
            .call()
            .chatResponse()
}