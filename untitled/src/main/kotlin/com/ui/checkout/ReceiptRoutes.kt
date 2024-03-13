package com.ui.checkout

import com.model.DataManagerMongoDB
import com.ui.Endpoints
import com.ui.login.Session
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Route.receipt(){
  get(Endpoints.RECEIPT.url){
    val session = call.sessions.get<Session>()
    call.respondHtmlTemplate(ReceiptTemplate(session, DataManagerMongoDB.INSTANCE.cartForUser(session))){
    }
  }
}