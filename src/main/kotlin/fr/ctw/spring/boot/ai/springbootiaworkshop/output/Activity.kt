package fr.ctw.spring.boot.ai.springbootiaworkshop.output

@JvmRecord
data class Activity(
    val activity: String,
    val location: String,
    val day: String,
    val time: String
)
