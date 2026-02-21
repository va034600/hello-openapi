package com.example

import com.example.api.HelloApi
import com.example.model.Hello2Request
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class HelloApplication

@RestController
class HelloController : HelloApi {
    override fun hello(): ResponseEntity<String> =
        ResponseEntity.ok("Hello, World!")

    override fun hello2(hello2Request: Hello2Request): ResponseEntity<String> =
        ResponseEntity.ok("Hello, ${hello2Request.name}!")
}

fun main(args: Array<String>) {
    runApplication<HelloApplication>(*args)
}
