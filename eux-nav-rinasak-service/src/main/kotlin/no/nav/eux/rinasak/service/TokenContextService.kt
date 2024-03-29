package no.nav.eux.rinasak.service

import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.stereotype.Service

@Service
class TokenContextService(
    val tokenValidationContextHolder: TokenValidationContextHolder
) {
    val navIdent
        get() = tokenValidationContextHolder
            .getTokenValidationContext()
            .firstValidToken
            ?.jwtTokenClaims
            ?.get("NAVident")
            ?.toString()
            ?: "ukjent"
}
