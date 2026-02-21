package com.example

import com.example.api.HelloApi
import com.example.model.Hello2Request
import com.example.model.Hello2Response
import com.github.f4b6a3.ulid.UlidCreator
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

    override fun hello2(hello2Request: Hello2Request): ResponseEntity<Hello2Response> =
        ResponseEntity.ok(
            Hello2Response(
                id = UlidCreator.getUlid().toString(),
                name = hello2Request.name,
            )
        )
}

fun main(args: Array<String>) {
    runApplication<HelloApplication>(*args)
}
