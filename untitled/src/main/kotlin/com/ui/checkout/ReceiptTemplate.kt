package com.ui.checkout

import com.model.Cart
import com.ui.Endpoints
import com.ui.GeneralViewTemplate
import com.ui.login.Session
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h5
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.style
import kotlinx.html.ul

class ReceiptTemplate(val session: Session?, val cart: Cart) : Template<HTML> {
  val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
  override fun HTML.apply() {
    insert(basicTemplate) {
      content {
        div(classes = "mt-2 row") {
          div(classes = "card") {
            this.style {
              +"width: 18rem;"
            }
            div(classes = "card-body") {
              h5(classes = "card-title") {
                +"Thank you for shopping at my Bookstore!"
              }
              p(classes = "card-text") {
                +"""You just purchased ${cart.qtyTotal} books for a total of ${cart.sum} gold coins.  
                                    The titles you purchased are listed here:"""
                ul {
                  cart.entries.forEach {
                    li {
                      +"${it.book.title} written by ${it.book.author}"
                    }
                  }
                }
              }
              p(classes = "card-text") {
                +"When you are done reading those come back and get 15% discount on your next purchase."
              }
              a(href = Endpoints.BOOKS.url, classes = "btn btn-primary") {
                +"Shop some more"
              }
            }
          }
        }
      }
    }
  }
}