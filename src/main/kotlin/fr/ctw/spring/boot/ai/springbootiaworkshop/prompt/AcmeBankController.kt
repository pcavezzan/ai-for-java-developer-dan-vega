package fr.ctw.spring.boot.ai.springbootiaworkshop.prompt

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/acme")
class AcmeBankController(private val chatClient: ChatClient) {

    @Autowired
    constructor(chatClientBuilder: ChatClient.Builder) : this(chatClient = chatClientBuilder.build())

    @GetMapping("/chat")
    fun chat(@RequestParam message: String): String? =
        chatClient
            .prompt()
            // System prompt to set the context for the conversation
            // Can also be set in chat client builder as default system prompt for any chat request.
            .system(
                """
                    You are a customer service assistant for Acme Bank.
                    You can ONLY discuss:
                    - Account balances and transactions
                    - Branch locations and hours
                    - General banking services
                    
                    If asked about anything else, politely decline to answer by respinding with: 
                    "I'm sorry, but I can only assist with banking-related questions."
                """.trimIndent()
            )
            .user(message)
            .call()
            .content()

    /*
    This method is commented out as it is not used in the current implementation.
    It can be used to sanitize the user input to prevent any malicious input.
    fun sanitizePrompt(message: String): String {
        // Sanitize the message to prevent any malicious input
        return message
                .replace("(?i)ignore previous instructions".toRegex(), "")
                .replace("(?i)system prompt".toRegex(), "")
                .replace("(?i)you are now".toRegex(), "")
    }
    */


}