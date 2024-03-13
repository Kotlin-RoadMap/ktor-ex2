package com.ui.cart

import com.model.Cart
import com.ui.Endpoints
import com.ui.GeneralViewTemplate
import com.ui.login.Session
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.ButtonType
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.HTML
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h2
import kotlinx.html.input
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr

class CartTemplate(val session: Session?, val cart: Cart) : Template<HTML> {
  val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
  override fun HTML.apply() {
    insert(basicTemplate) {
      content {
        div(classes = "mt-2 row") {
          h2() {
            +"Your shopping cart"
          }

          table(classes = "table table-striped") {
            thead {
              tr {
                th(scope = kotlinx.html.ThScope.col) { +"Title" }
                th(scope = kotlinx.html.ThScope.col) { +"Author" }
                th(scope = kotlinx.html.ThScope.col) { +"Price" }
                th(scope = kotlinx.html.ThScope.col) { +"Quantity" }
                th(scope = kotlinx.html.ThScope.col) { +"Sum" }
                th(scope = kotlinx.html.ThScope.col) { +"" }
              }
            }
            tbody {
              cart.entries.forEach() {
                tr {
                  td { +"${it.book.title}" }
                  td { +"${it.book.author}" }
                  td { +"${it.book.price}" }
                  td { +"${it.qty}" }
                  td { +"${it.sum}" }
                  td {
                    form(
                      method = kotlinx.html.FormMethod.post,
                      encType = kotlinx.html.FormEncType.multipartFormData,
                      action = Endpoints.DOREMOVEFROMCART.url
                    ) {
                      button(classes = "btn btn-success", type = kotlinx.html.ButtonType.submit) {
                        +"Remove 1 from cart"
                      }
                      input(type = kotlinx.html.InputType.hidden, name = "bookid") {
                        this.value = "${it.book.id}"
                      }
                    }
                  }
                }
              }
              tr {

              }
              tr {
                td {
                  +"Sum"
                }
                td {
                }
                td {
                }
                td {
                  +cart.qtyTotal.toString()
                }
                td {
                  +cart.sum.toString()
                }
              }
            }
          }

        }
        div(classes = "row mt-3") {
          form(
            method = FormMethod.get,
            encType = FormEncType.multipartFormData,
            action = Endpoints.RECEIPT.url
          ) {
            button(classes = "btn btn-warning", type = ButtonType.submit) {
              +"Pay and get a receipt"
            }
          }
        }
      }
    }
  }
}