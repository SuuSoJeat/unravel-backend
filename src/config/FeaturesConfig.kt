package config

import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.content.CachingOptions
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.util.date.GMTDate
import models.UserSession
import org.slf4j.event.Level

fun Sessions.Configuration.sessionsConfig() {
    cookie<UserSession>("USER_SESSION") {
        cookie.extensions["SameSite"] = "lax"
    }
}

@KtorExperimentalLocationsAPI
fun Locations.Configuration.locationsConfig() {
}

fun Compression.Configuration.compressionConfig() {
    gzip {
        priority = 1.0
    }
    deflate {
        priority = 10.0
        minimumSize(1024) // condition
    }
}

fun CallLogging.Configuration.callLoggingConfig() {
    level = Level.INFO
    filter { call -> call.request.path().startsWith("/") }
}

fun CORS.Configuration.corsConfig() {
    method(HttpMethod.Options)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Patch)
    header(HttpHeaders.Authorization)
    header("MyCustomHeader")
    allowCredentials = true
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
}

fun CachingHeaders.Configuration.cachingHeadersConfig() {
    options { outgoingContent ->
        when (outgoingContent.contentType?.withoutParameters()) {
            ContentType.Text.CSS -> CachingOptions(
                CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60),
                expires = null as? GMTDate?
            )
            else -> null
        }
    }
}

fun DefaultHeaders.Configuration.defaultHeadersConfig() {
    header("X-Engine", "Ktor") // will send this header with each response
}

fun PartialContent.Configuration.partialContentConfig() {
    // Maximum number of ranges that will be accepted from a HTTP request.
    // If the HTTP request specifies more ranges, they will all be merged into a single range.
    maxRangeCount = 10
}

fun ContentNegotiation.Configuration.contentNegotiationConfig() {
    gson {
        setPrettyPrinting()
    }
}