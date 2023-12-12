package no.nav.eux.rinasak.service

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BrukerService(
    val tokenValidationContextHolder: TokenValidationContextHolder
) {
     val log = logger {}

    fun bruker() =
        tokenValidationContextHolder
            .tokenValidationContext
            ?.firstValidToken
            ?.also { log.info { "firstValidToken=$it" } }
            ?.getOrNull()
            ?.also { log.info {  "getOrNull=$it" } }
            ?.jwtTokenClaims
            ?.also { log.info { "jwtTokenClaims.size=${it.allClaims.size}" } }
            ?.also { log.info { "jwtTokenClaims=${it}" } }
            ?.also { log.info { "jwtTokenClaims.keys=${it.allClaims.map { it.key }}" } }
            ?.get("NAVident")
            ?.also { log.info { "ident=$it" } }
            ?.toString()
            ?: "ukjent"
}
