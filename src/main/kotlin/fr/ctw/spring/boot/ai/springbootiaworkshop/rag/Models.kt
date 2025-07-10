package fr.ctw.spring.boot.ai.springbootiaworkshop.rag

@JvmRecord
data class Models(val model: List<Model>)

@JvmRecord
data class Model(val company: String, val model: String, val contextWindowSize: Int)
