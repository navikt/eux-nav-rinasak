package no.nav.eux.rinasak.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class TokenContextService {
    val navIdent: String
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return (authentication?.principal as? Jwt)?.getClaimAsString("NAVident") ?: "ukjent"
        }
}
