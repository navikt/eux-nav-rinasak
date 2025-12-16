package no.nav.eux.rinasak

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.zaxxer.hikari.HikariDataSource
import no.nav.eux.logging.RequestIdMdcFilter
import org.springframework.boot.jackson2.autoconfigure.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig(
    val dataSourceProperties: DataSourceProperties
) {

    @Bean
    fun hikariDataSource() = HikariDataSource(dataSourceProperties.hikari)

    @Bean
    fun requestIdMdcFilter() = RequestIdMdcFilter()

    @Suppress("removal")
    @Bean
    fun jackson2ObjectMapperBuilderCustomizer() = Jackson2ObjectMapperBuilderCustomizer { builder ->
        builder.featuresToDisable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        )
    }
}
