package no.nav.eux.rinasak.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@RestControllerAdvice
class IllegalArgumentExceptionAdvice {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentExceptions(exception: IllegalArgumentException): ResponseEntity<ApiError> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.apiError)

    val IllegalArgumentException.apiError
        get() = ApiError(
            timestamp = now(),
            message = message ?: "Illegal argument exception occurred"
        )

    data class ApiError(
        val timestamp: LocalDateTime,
        val message: String
    )
}
