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

## Database V1

````mermaid
erDiagram
    nav_rinasak {
        uuid            nav_rinasak_uuid PK         
        varchar(31)     rinasak_id "RINA CaseId, egentlig en INTEGER"
        varchar(31)     overstyrt_enhetsnummer "TBD"
        varchar(100)    opprettet_bruker "TBD"      
        timestamp       opprettet_dato   "TBD"      
    }
    fagsak {
        uuid            nav_rinasak_uuid PK, FK "Surrogate key, eller?"             
        varchar(100)    id  "Er dette den deprecated fagsak_id?"              
        varchar(100)    tema "TBD"            
        varchar(100)    system     "TBD"      
        varchar(100)    nr    "TBD"           
        varchar(100)    type   "TBD"          
        varchar(100)    opprettet_bruker "TBD"
        timestamp       opprettet_dato "TBD"
    }

    sed {
        uuid            sed_uuid PK  "Surrogate key, eller?"
        varchar(31)     id  "Er dette egentlig en EESSI DocumentId av typen UUID?"
        uuid            nav_rinasak_uuid FK   
        varchar(100)    dokument_info_id "TBD"
        varchar(100)    type             "TBD"
        varchar(100)    opprettet_bruker "TBD"
        timestamp       opprettet_dato   "TBD"
    }
    
    fagsak ||--|| nav_rinasak :  "er (stemmer kardinaliteten her?)"
    sed }|--|{ nav_rinasak :  "inngår i (hva er egentlig kardinaliteten her?)"
    

````

