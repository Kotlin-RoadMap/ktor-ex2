package com.learning.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.partialcontent.*

fun Application.configureHTTP() {
  install(PartialContent) {
    maxRangeCount = 10
  }
}
