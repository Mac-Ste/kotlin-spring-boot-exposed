package com.macste.spring

import com.macste.controllers.DebugController
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatusCode

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `test correct healthcheck response`() {
        val result = restTemplate.getForEntity("/debug/check", DebugController.HealthCheckResponse::class.java);

        result.statusCode shouldBe HttpStatusCode.valueOf(200)
        result.body.shouldNotBeNull()
        result.body!!.status shouldBe "UP"
    }

}