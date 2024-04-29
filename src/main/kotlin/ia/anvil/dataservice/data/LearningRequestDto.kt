package ia.anvil.dataservice.data

import ia.anvil.dataservice.service.LearningRequestDto
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LearningServiceDto {

    fun sendLearningRequest(learningRequest: LearningRequestDto) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity(learningRequest, headers)

        val restTemplate = RestTemplate()
        restTemplate.postForObject("http://localhost/api/endpoint", request, String::class.java)
    }
}