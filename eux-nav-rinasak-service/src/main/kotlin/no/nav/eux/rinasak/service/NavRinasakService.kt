package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.model.dto.NavRinasakPatch
import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.persistence.DokumentRepository
import no.nav.eux.rinasak.persistence.FagsakRepository
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class NavRinasakService(
    val navRinasakRepository: NavRinasakRepository,
    val fagsakRepository: FagsakRepository,
    val dokumentRepository: DokumentRepository,
) {

    @Transactional
    fun createNavRinasak(request: NavRinasakCreateRequest) {
        navRinasakRepository
            .findByRinasakId(request.rinasakId)
            ?.let { throw ResponseStatusException(CONFLICT, "NAV Rinasak finnes alt: ${request.rinasakId}") }
        navRinasakRepository.save(request.navRinasakEntity)
        request.initiellFagsakEntity?.let { fagsakRepository.save(it) }
        request.dokumentEntities.forEach { dokumentRepository.save(it) }
    }

    @Transactional
    fun patchNavRinasak(patch: NavRinasakPatch) {
        val eksisterende = findAllNavRinasaker(NavRinasakFinnRequest(rinasakId = patch.rinasakId))
            .singleOrNull()
            ?: throw ResponseStatusException(NOT_FOUND, "${patch.rinasakId} ikke funnet")
        navRinasakRepository.save(eksisterende.navRinasak.patch(patch))
        patch.initiellFagsak?.patch(eksisterende)
        patch.dokumenter?.let {
            eksisterende.dokumenter?.leggTil(patch, eksisterende.navRinasak.navRinasakUuid)
            eksisterende.dokumenter?.oppdater(patch, eksisterende.navRinasak.navRinasakUuid)
        }
    }

    fun NavRinasakPatch.DokumentPatch.sammeDokument(dokument: Dokument) =
        this.sedId == dokument.sedId && this.sedVersjon == dokument.sedVersjon

    fun NavRinasakPatch.InitiellFagsakPatch.patch(
        eksisterende: NavRinasakFinnResponse
    ) {
        entity(
            eksisterende.navRinasak.navRinasakUuid,
            eksisterende.navRinasak.opprettetBruker,
            eksisterende.navRinasak.opprettetTidspunkt
        ).let { fagsakRepository.save(it) }
    }

    fun findNavRinasakId(rinasakId: Int): NavRinasakFinnResponse {
        val navRinasak = navRinasakRepository
            .findAllByRinasakId(rinasakId)
            .singleOrNull()
            ?: throw ResponseStatusException(NOT_FOUND, "NAV Rinasak ikke funnet: $rinasakId")
        val fagsak = fagsakRepository
            .findByIdOrNull(navRinasak.navRinasakUuid)
        val dokumenter = dokumentRepository
            .findByNavRinasakUuid(navRinasak.navRinasakUuid)
        return NavRinasakFinnResponse(navRinasak, fagsak, dokumenter)
    }

    fun findAllNavRinasaker(request: NavRinasakFinnRequest): List<NavRinasakFinnResponse> {
        val navRinasakList = when {
            request.rinasakId != null -> navRinasakRepository.findAllByRinasakId(request.rinasakId!!)
            else -> navRinasakRepository.findAll()
        }
        val fagsakMap = fagsakRepository
            .findAllById(navRinasakList.map { it.navRinasakUuid })
            .associateBy { it.navRinasakUuid }
        val sedMap = dokumentRepository
            .findByNavRinasakUuidIn(navRinasakList.map { it.navRinasakUuid })
            .groupBy { it.navRinasakUuid }
        return navRinasakList.map {
            NavRinasakFinnResponse(it, fagsakMap[it.navRinasakUuid], sedMap[it.navRinasakUuid])
        }
    }

    fun List<Dokument>.leggTil(patch: NavRinasakPatch, navRinasakUuid: UUID) {
        patch
            .dokumenter
            ?.filterNot { patchDokument -> any { patchDokument.sammeDokument(it) } }
            ?.map { it.entity(navRinasakUuid) }
            ?.forEach { dokumentRepository.save(it) }
    }

    fun List<Dokument>.oppdater(patch: NavRinasakPatch, navRinasakUuid: UUID) {
        filter { eksisterer(patch, it) }
            .map { Pair(it, it.dokumentPatch(patch)) }
            .map { it.tilOppdatering() }
            .forEach { dokumentRepository.save(it) }
    }

    fun Pair<Dokument, NavRinasakPatch.DokumentPatch?>.tilOppdatering() =
        first.copy(
            dokumentInfoId = second?.dokumentInfoId ?: first.dokumentInfoId,
            sedType = second?.sedType ?: first.sedType
        )

    fun eksisterer(
        patch: NavRinasakPatch,
        dokument: Dokument
    ) =
        patch.dokumenter?.any { dokumentPatch -> dokumentPatch.sammeDokument(dokument) } == true

    fun Dokument.dokumentPatch(
        patch: NavRinasakPatch
    ): NavRinasakPatch.DokumentPatch? =
        patch.dokumenter?.single { it.sammeDokument(this) }

}
