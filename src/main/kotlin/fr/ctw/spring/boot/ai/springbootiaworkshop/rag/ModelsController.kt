package fr.ctw.spring.boot.ai.springbootiaworkshop.rag

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ModelsController(private val chatClient: ChatClient) {

    @Autowired
    constructor(
        chatClientBuilder: ChatClient.Builder,
        vectorStore: VectorStore
    ) : this(
        chatClient = chatClientBuilder
            .defaultAdvisors(QuestionAnswerAdvisor(vectorStore))
            .build()
    )

    @GetMapping("/rag/models")
    fun faq(
        @RequestParam(defaultValue = "Give me a list of all the models from OpenAI along with their context window.")
        message: String
    ) = chatClient.prompt()
        .user(message)
        .call()
        .entity(Models::class.java)
}