package fr.ctw.spring.boot.ai.springbootiaworkshop.byod

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ModelComparisonController(private val chatClient: ChatClient) {

    @Autowired
    constructor(chatClientBuilder: ChatClient.Builder) : this(chatClient = chatClientBuilder.build())

    @GetMapping("/models")
    fun models(): String? =
        chatClient
            .prompt("Can you give me an up to date list of popular large language models and their context window size ?")
            .call()
            .content()

    @GetMapping("/stuff-the-prompt")
    fun modelsStuffThePrompt(): String? {
        val system = """
            If you are asked about up to date language models and there context window, here is some information to help you with your answer:
            [
                { "company": "OpenAI", "model": "GPT-4o", "context_window_size": 128000 },
                { "company": "OpenAI", "model": "o1-preview", "context_window_size": 128000 },
               
                { "company": "Anthropic", "model": "Claude Opus 4", "contextWindow": 200000 },
                { "company": "Anthropic", "model": "Claude Sonnet 4", "contextWindow": 200000 },
                
                { "company": "Google", "model": "Gemini 2.5 Pro", "contextWindow": 1000000 },
                { "company": "Google", "model": "Gemini 2.0 Pro (Exp.)", "contextWindow": 2000000 },
                { "company": "Google", "model": "Gemini 2.0 Flash", "contextWindow": 1000000 },
                
                { "company": "Meta", "model": "Llama 3.1 405B", "contextWindow": 128000 },
               
                { "company": "xAI", "model": "Grok 3", "contextWindow": 1000000 },
                
                { "company": "Mistral AI", "model": "Mistral Large 2", "contextWindow": 128000 }, 
                
                { "company": "Alibaba Cloud", "model": "Qwen 2.5 72B", "contextWindow": 128000 },
                
                { "company": "DeepSeek", "model": "DeepSeek R1", "contextWindow": 128000 }
            ]
        """.trimIndent()
        return chatClient
            .prompt()
            .system(system)
            .user("Can you give me an up to date list of popular large language models and their context window size ?")
            .call()
            .content()
    }
}