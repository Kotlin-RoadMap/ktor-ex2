package com.learning.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
  val users = listOf<String>("shopper1", "shopper2", "shopper3")
  val admins = listOf<String>("admin")
  install(Authentication) {
    basic("bookStoreAuth") {
      realm = "Book store"
      validate {
        if ((users.contains(it.name) || admins.contains(it.name)) && it.password == "password")
          UserIdPrincipal(it.name)
        else
          null
      }
    }
  }

  authentication {
    basic(name = "myauth1") {
      realm = "Ktor Server"
      validate { credentials ->
        if (credentials.name == credentials.password) {
          UserIdPrincipal(credentials.name)
        } else {
          null
        }
      }
    }

    form(name = "myauth2") {
      userParamName = "user"
      passwordParamName = "password"
      challenge {
        /**/
      }
    }
  }
  data class MySession(val count: Int = 0)
  install(Sessions) {
    cookie<MySession>("MY_SESSION") {
      cookie.extensions["SameSite"] = "lax"
    }
  }
  routing {
    authenticate("bookStoreAuth") {
      get("/api/tryauth") {
        val principal = call.principal<UserIdPrincipal>()!!
        call.respondText("Hello ${principal.name}")
      }
    }
    get("/session/increment") {
      val session = call.sessions.get<MySession>() ?: MySession()
      call.sessions.set(session.copy(count = session.count + 1))
      call.respondText("Counter is ${session.count}. Refresh to increment.")
    }
  }
}
