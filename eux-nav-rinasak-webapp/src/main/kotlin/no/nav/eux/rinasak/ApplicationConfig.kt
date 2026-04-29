package no.nav.eux.rinasak

import com.zaxxer.hikari.HikariDataSource
import no.nav.eux.logging.RequestIdMdcFilter
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.DeserializationFeature
import tools.jackson.databind.cfg.DateTimeFeature

@Configuration
class ApplicationConfig(
    val dataSourceProperties: DataSourceProperties
) {

    @Bean
    fun hikariDataSource() = HikariDataSource(dataSourceProperties.hikari)

    @Bean
    fun requestIdMdcFilter() = RequestIdMdcFilter()

    @Bean
    fun jsonMapperBuilderCustomizer() = JsonMapperBuilderCustomizer { builder ->
        builder
            .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
}
