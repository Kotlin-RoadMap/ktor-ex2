package com.ui.cart

import com.model.DataManagerMongoDB
import com.ui.Endpoints
import com.ui.books.BookTemplate
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

fun Route.cart(){
  get(Endpoints.CART.url){
    val session = call.sessions.get<Session>()
    call.respondHtmlTemplate(CartTemplate(session
      , DataManagerMongoDB.INSTANCE.cartForUser(session))){}
  }
  post(Endpoints.DOADDTOCART.url){
    val log = LoggerFactory.getLogger("LoginView")
    val multipart = call.receiveMultipart()
    val session = call.sessions.get<Session>()
    var bookid: String =""
    while (true) {
      val part = multipart.readPart() ?: break
      when (part) {
        is PartData.FormItem -> {
          log.info("FormItem: ${part.name} = ${part.value}")
          if (part.name == "bookid")
            bookid = part.value
        } else -> {

        }
      }
      part.dispose()
    }
    val book = DataManagerMongoDB.INSTANCE.getBookWithId(bookid)
    DataManagerMongoDB.INSTANCE.addBook(session, book)
    call.respondHtmlTemplate(BookTemplate(call.sessions.get<Session>(), DataManagerMongoDB.INSTANCE.allBooks())){
      searchfilter{
        i{
          +"Book added to cart"
        }
      }
    }
  }

  post(Endpoints.DOREMOVEFROMCART.url){
    val log = LoggerFactory.getLogger("Remove from cart")
    val multipart = call.receiveMultipart()
    val session = call.sessions.get<Session>()
    var bookid: String =""
    while (true) {
      val part = multipart.readPart() ?: break
      when (part) {
        is PartData.FormItem -> {
          log.info("FormItem: ${part.name} = ${part.value}")
          if (part.name == "bookid")
            bookid = part.value
        } else -> {

        }
      }
      part.dispose()
    }
    val book = DataManagerMongoDB.INSTANCE.getBookWithId(bookid)
    DataManagerMongoDB.INSTANCE.removeBook(session, book)
    call.respondHtmlTemplate(CartTemplate(session, DataManagerMongoDB.INSTANCE.cartForUser(session))){
    }
  }
}