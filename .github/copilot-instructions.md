# Copilot Instructions for eux-nav-rinasak

## Build & Test

```bash
# Full build (requires PostgreSQL running on localhost:5432)
mvn clean install

# Run a single test class
mvn test -pl eux-nav-rinasak-webapp -Dtest="RinasakerApiOpprettelseTest"

# Run a single test method
mvn test -pl eux-nav-rinasak-webapp -Dtest="RinasakerApiOpprettelseTest#POST rinasaker - gyldig forespørsel - 201"

# Fast build (skip tests)
mvn clean install -DskipTests
```

Tests require a PostgreSQL database. Set these environment variables:

```bash
export DATABASE_HOST=localhost
export DATABASE_PORT=5432
export DATABASE_DATABASE=postgres
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres
```

## Architecture

This is an API-first Spring Boot service that links NAV cases (fagsaker) to RINA cases (rinasaker). It follows a strict multi-module layered architecture:

```
eux-nav-rinasak-webapp      → REST controllers (implement generated OpenAPI interfaces)
eux-nav-rinasak-service     → Business logic (@Service, @Transactional)
eux-nav-rinasak-persistence → Spring Data JPA repositories, Flyway migrations
eux-nav-rinasak-model       → JPA entities (@Entity data classes) and DTOs
eux-nav-rinasak-openapi     → OpenAPI spec + generated Kotlin interfaces
```

**API-first workflow**: The OpenAPI spec lives in `eux-nav-rinasak-openapi/src/main/resources/static/nav-rinasak-v1.yaml`. The `openapi-generator-maven-plugin` generates Kotlin Spring interfaces during `generate-sources`. Controllers in the webapp module implement these generated interfaces (e.g., `RinasakerApiImpl : RinasakerApi`). When adding or changing endpoints, modify the OpenAPI spec first, then rebuild to regenerate interfaces.

**Central entity**: `NavRinasak` is the root entity. Related entities (`InitiellFagsak`, `Fagsak`, `Dokument`) are linked via `nav_rinasak_uuid`. `SedJournalstatus` tracks journal status of SED documents.

## Key Conventions

### Mapping between layers

OpenAPI-generated types are mapped to internal DTOs/entities using top-level Kotlin extension functions in `*Mapper.kt` files. The pattern is:

- `OpenApiType.toInternalDto()` — converts inbound API types to DTOs
- `Entity.toOpenApiType()` — converts entities to outbound API types
- Extension properties for concise access: `val NavRinasakPatchType.navRinasakPatch get() = ...`

Response wrapping uses extension functions from `ResponseEntityMapper.kt`: `.toOkResponseEntity()`, `.toCreatedEmptyResponseEntity()`, `.toNoContentEmptyResponseEntity()`.

### Entity updates

JPA entities are Kotlin `data class`es. Updates use `copy()` via `patch()` methods on the entity itself:

```kotlin
fun patch(patch: NavRinasakPatch) = this.copy(overstyrtEnhetsnummer = patch.overstyrtEnhetsnummer ?: overstyrtEnhetsnummer)
```

### Service layer

Services use constructor injection, are annotated `@Transactional`, and throw `ResponseStatusException` for error cases (409 CONFLICT, 404 NOT_FOUND). The `TokenContextService` extracts `NAVident` from the JWT token for audit fields.

### MDC logging

Services use `.mdc(rinasakId = ...)` from `eux-logging` to set MDC context before chaining operations.

### Database migrations

Flyway migrations are in `eux-nav-rinasak-persistence/src/main/resources/db/migration/`. Follow the existing naming pattern: `V{next}__description.sql`.

### Testing

All tests are integration tests in the webapp module, extending `AbstractRinasakerApiImplTest`. They use `@SpringBootTest` with a real PostgreSQL database, `MockOAuth2Server` for authentication, and Kotest matchers (`shouldBe`). The base class cleans all tables between tests. Test data is built using dataset builder objects in `webapp/dataset/`.

### Language

Code identifiers, database columns, and API field names are in Norwegian (e.g., `opprettetBruker`, `opprettetTidspunkt`, `fagsak`). Maintain this convention.
