package no.nav.eux.rinasak.service

import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BrukerService(
    val tokenValidationContextHolder: TokenValidationContextHolder
) {

    fun bruker() =
        tokenValidationContextHolder
            .tokenValidationContext
            ?.firstValidToken
            ?.getOrNull()
            ?.jwtTokenClaims
            ?.get("NAVident")
            ?.toString()
            ?: "ukjent"
}
