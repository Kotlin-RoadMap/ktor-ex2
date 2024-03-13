package com.ui.books

import com.model.DataManagerMongoDB
import com.ui.Endpoints
import com.ui.login.Session
import io.ktor.http.content.PartData
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.request.receiveMultipart
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import kotlinx.html.i
import org.slf4j.LoggerFactory

fun Route.books(){
  get(Endpoints.BOOKS.url){
    call.respondHtmlTemplate(BookTemplate(call.sessions.get<Session>(),
      DataManagerMongoDB.INSTANCE.allBooks())){
    }
  }
  post(Endpoints.DOBOOKSEARCH.url){
    val log = LoggerFactory.getLogger("LoginView")
    val multipart = call.receiveMultipart()
    var search: String =""
    while (true) {
      val part = multipart.readPart() ?: break
      when (part) {
        is PartData.FormItem -> {
          log.info("FormItem: ${part.name} = ${part.value}")
          if (part.name == "search")
            search = part.value
        } else -> {
        }
      }
      part.dispose()
    }
    val searchBooks = DataManagerMongoDB.INSTANCE.searchBooks(search)
    call.respondHtmlTemplate(BookTemplate(call.sessions.get<Session>(), searchBooks)){
      searchfilter{
        i{
          +"Search filter: $search"
        }
      }
    }
  }
}