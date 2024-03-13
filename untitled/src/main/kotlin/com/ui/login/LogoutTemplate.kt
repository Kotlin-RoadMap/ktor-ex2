package com.ui.login

import com.ui.GeneralViewTemplate
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h2

class LogoutTemplate(val session: Session?) : Template<HTML> {
  val basicTemplate: GeneralViewTemplate = GeneralViewTemplate(session)
  override fun HTML.apply() {
    insert(basicTemplate) {
      content {
        div(classes = "mt-2") {
          h2() {
            +"You have been logged out!"
          }
        }
      }
    }
  }
}