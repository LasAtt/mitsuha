package io.serd.mitsuha

import io.serd.mitsuha.dao.ImageRepository
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@SpringBootApplication
class MitsuhaApplication {
    @Bean
    fun transactionManager(@Qualifier("dataSource") dataSource: DataSource) = SpringTransactionManager(dataSource)

    @Bean
    fun init(imageRepository: ImageRepository) = CommandLineRunner {
        imageRepository.createTable()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MitsuhaApplication::class.java, *args)
}
