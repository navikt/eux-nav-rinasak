### EUX NAV Rinasak

Sammenknytning av NAV og Rina-saker.

## Brukte teknologier
* Kotlin
* Spring
* Maven

#### Avhengigheter

* JDK 17

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
class fagsak {
   varchar(100) id
   varchar(100) tema
   varchar(100) system
   varchar(100) nr
   varchar(100) type
   varchar(100) opprettet_bruker
   timestamp opprettet_dato
   uuid nav_rinasak_uuid
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
class nav_rinasak {
   varchar(31) rinasak_id
   varchar(31) overstyrt_enhetsnummer
   varchar(100) opprettet_bruker
   timestamp opprettet_dato
   uuid nav_rinasak_uuid
}
class sed {
   varchar(31) id
   uuid nav_rinasak_uuid
   varchar(100) dokument_info_id
   varchar(100) type
   varchar(100) opprettet_bruker
   timestamp opprettet_dato
   uuid sed_uuid
}

fagsak  -->  nav_rinasak : nav_rinasak_uuid
sed  -->  nav_rinasak : nav_rinasak_uuid

````