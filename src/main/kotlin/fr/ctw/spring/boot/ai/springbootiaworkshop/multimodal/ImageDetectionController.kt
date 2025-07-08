package fr.ctw.spring.boot.ai.springbootiaworkshop.multimodal

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ImageDetectionController(
    private val chatClient: ChatClient
) {
    @Value("classpath:/images/sample.jpg")
    private lateinit var sampleImage: Resource

    @Autowired
    constructor(chatClientBuilder: ChatClient.Builder) : this(chatClient = chatClientBuilder.build())

    @GetMapping("/image-to-text")
    fun imageToText(): String? =
        chatClient
            .prompt()
            .user {
                it.text("Can you please describe what you see in the following image ?")
                    .media(MimeTypeUtils.IMAGE_JPEG, sampleImage)
            }
            .call()
            .content()
}