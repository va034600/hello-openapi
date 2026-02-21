package com.example

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `GET hello で Hello World が返る`() {
        mockMvc.get("/hello")
            .andExpect {
                status { isOk() }
                content { string("Hello, World!") }
            }
    }

    @Test
    fun `POST hello2 で id と input_name が返る`() {
        mockMvc.post("/hello2") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "Alice"}"""
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { isNotEmpty() }
            jsonPath("$.name") { value("Alice") }
        }
    }

    @Test
    fun `POST hello2 で name が未指定の場合 400 が返る`() {
        mockMvc.post("/hello2") {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `POST hello2 で name が257文字の場合 400 が返る`() {
        val longName = "a".repeat(257)
        mockMvc.post("/hello2") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "$longName"}"""
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `POST hello2 で name が256文字の場合 成功する`() {
        val name = "a".repeat(256)
        mockMvc.post("/hello2") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "$name"}"""
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { isNotEmpty() }
            jsonPath("$.name") { value(name) }
        }
    }
}
