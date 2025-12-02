package no.nav.eux.rinasak.webapp

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity

fun <T> T.toOkResponseEntity() = ResponseEntity<T>(this, OK)

fun <T> T.toCreatedEmptyResponseEntity() = ResponseEntity<T>(this, CREATED)

fun <T> T.toNoContentEmptyResponseEntity() = ResponseEntity<T>(this, HttpStatus.NO_CONTENT)
