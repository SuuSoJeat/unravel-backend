import config.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.TextContent
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.Sessions
import org.koin.core.context.startKoin
import routes.homeRoute

fun main(args: Array<String>) {
    startKoin {
        printLogger()
        modules(listOf(applicationModule))
    }
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Locations) { locationsConfig() }
    install(Sessions) { sessionsConfig() }
    install(Compression) { compressionConfig() }
    install(AutoHeadResponse)
    install(CallLogging) { callLoggingConfig() }
    install(ConditionalHeaders)
    install(CORS) { corsConfig() }
    install(CachingHeaders) { cachingHeadersConfig() }
    install(DataConversion)
    install(DefaultHeaders) { defaultHeadersConfig() }
    install(PartialContent) { partialContentConfig() }
    install(ContentNegotiation) { contentNegotiationConfig() }
    install(StatusPages) {
//        status(HttpStatusCode.NotFound) {
//            call.respond(TextContent("${it.value} ${it.description}", ContentType.Text.Plain.withCharset(Charsets.UTF_8), it))
//        }
    }

    routing {
        homeRoute()
        route("api/v1") {

        }
    }
}
