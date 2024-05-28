package ia.anvil.dataservice.service

import java.io.FileInputStream
import java.util.Properties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
// Define a classe de transferência de dados para a requisição de aprendizado
data class LearningRequestDto(
    val subject: String,
    val difficulty: String
)
// faz a requisição para a API do GPT-3 para gerar uma pergunta
@Service
class LearningService {

    fun loadApiKeys(): Properties {
    val properties = Properties()
    val inputStream = FileInputStream("application-secrets.properties")
    properties.load(inputStream)
    return properties
    }

    // Variável para armazenar a pergunta gerada
    private var generatedQuestion: String? = null

    fun generateQuestion(learningRequest: LearningRequestDto) {
        val gpt3ApiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions"
        val prompt = "Generate a ${learningRequest.difficulty} question about ${learningRequest.subject}."
        val maxTokens = 60

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val apiKeys = loadApiKeys()
        headers.set("Authorization", "Bearer " + apiKeys.getProperty("openai.api.key"))

        val body = mapOf("prompt" to prompt, "max_tokens" to maxTokens)
        val request = HttpEntity(body, headers)

        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.postForObject(gpt3ApiUrl, request, String::class.java)
            // Armazenando a resposta
            generatedQuestion = response
        } catch (e: Exception) {
            println("Erro ao fazer a chamada de API: ${e.message}")
            generatedQuestion = null
        }
    }
    // retorna a pergunta gerada
    fun getQuestion(): String {
        return generatedQuestion ?: "Nenhuma pergunta foi gerada ainda"
    }
}