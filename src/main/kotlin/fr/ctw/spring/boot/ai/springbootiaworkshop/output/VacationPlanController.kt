package fr.ctw.spring.boot.ai.springbootiaworkshop.output

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VacationPlanController(private val chatClient: ChatClient) {

    @Autowired
    constructor(chatClientBuilder: ChatClient.Builder) : this(chatClient = chatClientBuilder.build())

    @GetMapping("/vacation/unstructured")
    fun unstructuredVacationPlan(): String? =
        chatClient
            .prompt()
            .user("I want to plan a trip to Hawaii. Give me a list of things to do.")
            .call()
            .content()

    @GetMapping("/vacation/structured")
    fun structuredVacationPlan(): Itinerary? =
        chatClient
            .prompt()
            .user("I want to plan a trip to Hawaii. Give me a list of things to do.")
            .call()
            .entity(Itinerary::class.java)
}