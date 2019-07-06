package routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.sessions.getOrSet
import io.ktor.sessions.sessions
import models.UserSession
import services.NicknameGenerator
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
@Location("/")
object Home

@KtorExperimentalLocationsAPI
fun Route.homeRoute() {
    val nicknameGenerator: NicknameGenerator by inject()

    get<Home> {
        val userSession = call.sessions.getOrSet { UserSession(nickname = nicknameGenerator.generateOne()) }
        call.respondText("${userSession.nickname} said there's no home.", contentType = ContentType.Text.Plain)
    }
}