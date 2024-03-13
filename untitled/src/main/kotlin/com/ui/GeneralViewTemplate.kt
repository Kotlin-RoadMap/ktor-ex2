package com.ui

import com.ui.login.Session
import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.HTML
import kotlinx.html.HtmlBlockTag
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.title

class GeneralViewTemplate(val session: Session?) : Template<HTML> {
  val content = Placeholder<HtmlBlockTag>()

  override fun HTML.apply() {
    head {
      link(
        rel = "stylesheet",
        href = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css",
        type = "text/css"
      ) {
        this.integrity = "sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
        this.attributes.put("crossorigin", "anonymous")
      }
      title("Bookstore application")
    }

    body {
      insert(NavigationTemplate(session)){
        menuitems {
          a(classes = "nav-link", href = Endpoints.HOME.url) {
            +"Home"
          }
        }
        if (session == null) {

          menuitems {
            a(classes = "nav-link", href = Endpoints.LOGIN.url) {
              +"Login"
            }
          }
        }
        else{
          menuitems {
            a(classes = "nav-link", href = Endpoints.LOGOUT.url) {
              +"Logout"
            }
          }
          menuitems {
            a(classes = "nav-link", href = Endpoints.BOOKS.url) {
              +"Books"
            }
          }
        }
      }

      div(classes = "container") {
        div(classes = "row") {
          div(classes = "col-md-12") {
            insert(content)
          }
        }
      }
      script(type = "javascript", src = "https://code.jquery.com/jquery-3.5.1.slim.min.js") {
        integrity = "sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        this.attributes.put("crossorigin", "anonymous")
      }
      script(
        type = "javascript",
        src = "https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
      ) {
        integrity = "sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        this.attributes.put("crossorigin", "anonymous")
      }
      script(
        type = "javascript",
        src = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
      ) {
        integrity = "sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        this.attributes.put("crossorigin", "anonymous")
      }
    }

  }

}