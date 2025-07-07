package fr.ctw.spring.boot.ai.springbootiaworkshop.prompt

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(private val chatClient: ChatClient) {

    @Autowired
    constructor(chatBuilder: ChatClient.Builder) : this(chatClient = chatBuilder.build())

    @GetMapping("/posts/new")
    fun newPost(@RequestParam(value = "topic", defaultValue = "JDK Virtual Threads") topic: String): String? =
        chatClient
            .prompt()
            // A system message in LLMs is a special type of input that provides high-level instructions, context,
            // or behavioral guidelines for the model before it processes user queries.
            // Think of it as the "behind-the-scenes" that shape how the AI should respond.
            //
            // Use it as a guide or a restriction to the model's behavior.
            .system(
                """
                    Blog Post Generator Guidelines:
                    
                    1. Length & Purpose: Generate 500-word blog posts that inform and engage general audiences.
                    
                    2. Structure:
                        - Introduction: Hook readers and establish the topic's relevance
                        - Body: Develop 3 main points with supporting evidence and examples
                        - Conclusion: Summarize key takeaways and include a call-to-action
                        
                    3. Content Requirements:
                        - Include real-world applications or case studies
                        - Incorporate relevant statistics or data points when appropriate
                        - Explain benefits/implications clearly for non-experts
                        
                    4. Style & Tone:
                        - Write in an informative yet conversational voice
                        - Use accessible language while maintaining authority
                        - Break up text with subheadings and short paragraphs
                        
                    5. Response format: Deliver complete, ready-to-public posts with a suggested title.
                """.trimIndent()
            )
            .user {
                it
                    .text("Write me a blog post about {topic}")
                    .param("topic", topic)
            }
            .call()
            .content()
}