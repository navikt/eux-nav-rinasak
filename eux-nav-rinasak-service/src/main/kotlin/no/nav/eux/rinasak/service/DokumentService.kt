package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.DokumentCreateRequest
import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.persistence.DokumentRepository
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class DokumentService(
    val dokumentRepository: DokumentRepository,
    val navRinasakRepository: NavRinasakRepository
) {

    @Transactional
    fun createDokument(request: DokumentCreateRequest) {
        val eksisterendeDokumenter = dokumentRepository
            .findBySedIdAndSedVersjon(request.sedId, request.sedVersjon)
        if (eksisterendeDokumenter.isNotEmpty())
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Dokument eksisterer med sedId=${request.sedId} og sedVersjon=${request.sedVersjon}"
            )
        val rinasak = navRinasakRepository
            .findAllByRinasakId(request.rinasakId)
            .single()
        dokumentRepository.save(
            Dokument(
                dokumentUuid = request.dokumentUuid,
                navRinasakUuid = rinasak.navRinasakUuid,
                sedId = request.sedId,
                sedVersjon = request.sedVersjon,
                sedType = request.sedType,
                dokumentInfoId = request.dokumentInfoId,
                opprettetBruker = request.opprettetBruker
            )
        )
    }
}
