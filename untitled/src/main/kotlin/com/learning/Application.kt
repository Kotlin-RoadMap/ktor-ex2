package com.learning

import com.learning.plugins.configureHTTP
import com.learning.plugins.configureMonitoring
import com.learning.plugins.configureRouting
import com.learning.plugins.configureSecurity
import com.learning.plugins.configureSerialization
import com.learning.plugins.configureTemplating
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module() {
  configureSecurity()
  configureHTTP()
  configureTemplating()
  configureMonitoring()
  configureSerialization()
  configureRouting()

  val client = HttpClient(Apache) {
    install(ContentNegotiation) {
      gson()
    }
  }
}
