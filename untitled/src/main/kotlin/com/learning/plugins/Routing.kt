package com.learning.plugins

import com.learning.books
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
  install(Resources)
  install(StatusPages) {
    exception<Throwable> { call, cause ->
      call.respond(HttpStatusCode.InternalServerError)
      throw cause
    }
  }
  routing {
    books()

    get("/") {
      call.respondText("Hello World!")
    }

    // Static plugin. Try to access `/static/index.html`
    static("/static") {
      resources("static")
    }
  }
}
