package fr.ctw.spring.boot.ai.springbootiaworkshop.rag

import org.slf4j.LoggerFactory
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.reader.TextReader
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import java.io.File

@Configuration
class RagConfiguration(
    @Value("classpath:/data/models.json")
    private val models: Resource
) {

    private val vectorStoreName: String = "vector-store.json"

    @Bean
    fun simpleVectorStore(embeddingModel: EmbeddingModel): SimpleVectorStore {
        val simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build()
        val classPathDirPath = ClassPathResource("/data").file.absolutePath
        val vectorStoreFilePath = "$classPathDirPath/$vectorStoreName"
        val vectorStoreFile = File(vectorStoreFilePath)
        if (vectorStoreFile.exists()) {
            LOGGER.info("Vector store file found at {}. Loading existing vector store.", vectorStoreFilePath)
            simpleVectorStore.load(vectorStoreFile)
        } else {
            LOGGER.info(
                "Vector store file not found at {}. A new vector store will be created.",
                vectorStoreFilePath
            )
            val textReader = TextReader(models)
            val customMetadata = textReader.customMetadata
            customMetadata.put("filename", "models.txt")
            val documents = textReader.get()
            val tokenTextSplitter = TokenTextSplitter()
            val splitDocuments = tokenTextSplitter.apply(documents)

            simpleVectorStore.add(splitDocuments)
            simpleVectorStore.save(vectorStoreFile)
        }
        return simpleVectorStore
    }

    companion object {
        @JvmStatic
        private val LOGGER = LoggerFactory.getLogger(RagConfiguration::class.java)
    }
}