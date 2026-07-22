package no.nav.eux.rinasak.webapp.common

import com.nimbusds.jose.JOSEObjectType
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback

val MockOAuth2Server.token: String
    get() = this
        .issueToken("issuer1", "theclientid", tokenCallback)
        .serialize()

private val tokenCallback =
    DefaultOAuth2TokenCallback(
        "issuer1",
        "subject1",
        JOSEObjectType.JWT.type,
        listOf("demoapplication"),
        emptyMap(),
        3600
    )
