package ia.anvil.dataservice

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestDataServiceApplication {

    @Bean
    @ServiceConnection
    fun mongoDbContainer(): MongoDBContainer {
        return MongoDBContainer(DockerImageName.parse("mongo:latest"))
    }

}

fun main(args: Array<String>) {
    fromApplication<DataServiceApplication>().with(TestDataServiceApplication::class).run(*args)
}
