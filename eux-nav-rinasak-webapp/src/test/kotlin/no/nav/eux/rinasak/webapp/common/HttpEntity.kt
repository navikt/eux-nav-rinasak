package no.nav.eux.rinasak.webapp.common

import com.nimbusds.jose.JOSEObjectType
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

fun <T> T.httpEntity(mockOAuth2Server: MockOAuth2Server): HttpEntity<T> {
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_JSON
    headers.set("Authorization", "Bearer ${mockOAuth2Server.token}")
    return HttpEntity<T>(this, headers)
}

val MockOAuth2Server.token: String
    get() = this
        .issueToken("issuer1", "theclientid", defaultOAuth2TokenCallback)
        .serialize()

var defaultOAuth2TokenCallback =
    DefaultOAuth2TokenCallback(
        "issuer1",
        "subject1",
        JOSEObjectType.JWT.type,
        listOf("demoapplication"),
        emptyMap(),
        3600
    )
