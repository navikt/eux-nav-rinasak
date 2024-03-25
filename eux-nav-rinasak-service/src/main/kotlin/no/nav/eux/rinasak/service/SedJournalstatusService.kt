package no.nav.eux.rinasak.service

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

    @Transactional
    fun save(
        sedId: UUID,
        sedVersjon: Int,
        status: SedJournalstatus.Status,
    ) {
        println("finner.....")
        val current = repository.findBySedIdAndSedVersjon(sedId, sedVersjon)
        println("current:::  $current")

        when {
            current == null -> create(sedId, sedVersjon, status)
            else -> repository.save(current.copy(status = status))
        }
    }

    fun finn(
        sedId: UUID,
        sedVersjon: Int
    ) =
        repository
            .findBySedIdAndSedVersjon(sedId, sedVersjon)

    private fun create(
        sedId: UUID,
        sedVersjon: Int,
        status: SedJournalstatus.Status,
    ) {
        repository.save(
            SedJournalstatus(
                sedJournalstatusUuid = randomUUID(),
                sedId = sedId,
                sedVersjon = sedVersjon,
                status = status,
                opprettetBruker = contextService.navIdent,
                endretBruker = contextService.navIdent
            )
        )
        println("                     laget!")
    }
}