package no.nav.eux.rinasak.service

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import no.nav.eux.rinasak.model.entity.SedJournalstatus
import no.nav.eux.rinasak.persistence.SedJournalstatusRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.UUID.randomUUID

@Service
class SedJournalstatusService(
    val repository: SedJournalstatusRepository,
    val contextService: TokenContextService,
) {

    val log = logger {}

    @Transactional
    fun save(
        rinasakId: Int,
        sedId: UUID,
        sedVersjon: Int,
        status: SedJournalstatus.Status,
    ) {
        val current = repository
            .findBySedIdAndSedVersjon(sedId, sedVersjon)
            .firstOrNull()
        when {
            current == null -> create(rinasakId, sedId, sedVersjon, status)
            else -> repository.save(current.copy(status = status))
        }
        log.info { "Sed journalstatus satt til $status" }
    }

    fun finn(
        rinasakId: Int?,
        sedId: UUID?,
        sedVersjon: Int?,
        status: SedJournalstatus.Status?,
    ) =
        when {
            sedId != null && sedVersjon != null -> finn(sedId, sedVersjon)
            rinasakId != null -> repository.findByRinasakId(rinasakId)
            status != null -> repository.findByStatus(status)
            else -> throw IllegalArgumentException(
                "sedId og sedVersjon, rinasakId eller status må være satt"
            )
        }

    fun finn(sedId: UUID, sedVersjon: Int) =
        repository.findBySedIdAndSedVersjon(sedId, sedVersjon)

    private fun create(
        rinasakId: Int,
        sedId: UUID,
        sedVersjon: Int,
        status: SedJournalstatus.Status,
    ) {
        repository.save(
            SedJournalstatus(
                rinasakId = rinasakId,
                sedJournalstatusUuid = randomUUID(),
                sedId = sedId,
                sedVersjon = sedVersjon,
                status = status,
                opprettetBruker = contextService.navIdent,
                endretBruker = contextService.navIdent
            )
        )
    }
}
