### EUX Nav Rinasak

Sammenknytning av nav og Rina-saker.

## Brukte teknologier
* Kotlin
* Spring
* Maven

#### Avhengigheter

* JDK 21

#### Kjøring av tester lokalt

Kjøring av database-tester krever en kjørende PostgreSQL database med følgende variabler satt korrekt:

```
set -x DATABASE_HOST localhost
set -x DATABASE_USERNAME postgres
set -x DATABASE_DATABASE postgres
set -x DATABASE_PORT 5432
```

## Database

````mermaid
classDiagram
direction BT
class dokument {
   varchar(100) dokument_info_id
   uuid sed_id
   integer sed_versjon
   varchar(100) sed_type
   uuid nav_rinasak_uuid
   varchar(100) opprettet_bruker
   timestamp opprettet_tidspunkt
   uuid dokument_uuid
}
class flyway_schema_history {
   varchar(50) version
   varchar(200) description
   varchar(20) type
   varchar(1000) script
   integer checksum
   varchar(100) installed_by
   timestamp installed_on
   integer execution_time
   boolean success
   integer installed_rank
}
class initiell_fagsak {
   varchar(100) id
   varchar(100) tema
   varchar(100) system
   varchar(100) nr
   varchar(100) type
   varchar(100) arkiv
   varchar(100) fnr
   varchar(100) opprettet_bruker
   timestamp opprettet_tidspunkt
   uuid nav_rinasak_uuid
}
class nav_rinasak {
   integer rinasak_id
   varchar(31) overstyrt_enhetsnummer
   varchar(100) opprettet_bruker
   timestamp opprettet_tidspunkt
   uuid nav_rinasak_uuid
}

dokument  -->  nav_rinasak : nav_rinasak_uuid
initiell_fagsak  -->  nav_rinasak : nav_rinasak_uuid
````