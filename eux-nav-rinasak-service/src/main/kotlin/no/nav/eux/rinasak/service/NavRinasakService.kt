package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.model.dto.NavRinasakPatch
import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.persistence.DokumentRepository
import no.nav.eux.rinasak.persistence.FagsakRepository
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NavRinasakService(
    val navRinasakRepository: NavRinasakRepository,
    val fagsakRepository: FagsakRepository,
    val dokumentRepository: DokumentRepository,
) {

    @Transactional
    fun createNavRinasak(request: NavRinasakCreateRequest) {
        navRinasakRepository.save(request.navRinasakEntity)
        request.initiellFagsakEntity?.let { fagsakRepository.save(it) }
        request.dokumentEntities.forEach { dokumentRepository.save(it) }
    }

    @Transactional
    fun patchNavRinasak(patch: NavRinasakPatch) {
        val eksisterende = findAllNavRinasaker(NavRinasakFinnRequest(rinasakId = patch.rinasakId))
            .singleOrNull()
            ?: throw RuntimeException("${patch.rinasakId} ikke funnet")
        navRinasakRepository.save(eksisterende.navRinasak.patch(patch))
        patch.initiellFagsak?.patch(eksisterende)
        patch.dokumenter?.let {
            eksisterende.dokumenter?.leggTil(patch, eksisterende.navRinasak.navRinasakUuid)
            eksisterende.dokumenter?.oppdater(patch, eksisterende.navRinasak.navRinasakUuid)
        }
    }

    fun NavRinasakPatch.DokumentPatch.sammeSed(dokument: Dokument) =
        this.sedId == dokument.sedId && this.sedVersjon == dokument.sedVersjon

    fun NavRinasakPatch.InitiellFagsakPatch.patch(
        eksisterende: NavRinasakFinnResponse
    ) {
        entity(
            eksisterende.navRinasak.navRinasakUuid,
            eksisterende.navRinasak.opprettetBruker,
            eksisterende.navRinasak.opprettetDato
        ).let { fagsakRepository.save(it) }
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
            ?.filterNot { patchDokument -> any { patchDokument.sammeSed(it) } }
            ?.map { it.entity(navRinasakUuid) }
            ?.forEach { dokumentRepository.save(it) }
    }

    fun List<Dokument>.oppdater(patch: NavRinasakPatch, navRinasakUuid: UUID) {
        patch
            .dokumenter
            ?.filter { patchDokument -> any { patchDokument.sammeSed(it) } }
            ?.map { it.entity(navRinasakUuid) }
            ?.forEach { dokumentRepository.save(it) }
    }
}
