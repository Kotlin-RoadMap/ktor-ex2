package com.learning

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

@Resource("/api/book/list")
class BookListResource(val sortby: String, val asc: Boolean)

fun Route.books() {
  val dataManager = DataManagerMongoDB()

  authenticate("bookStoreAuth") {
    get<BookListResource>() {
      call.respond(dataManager.sortedBooks(it.sortby, it.asc))
    }
  }
}