CREATE TABLE fagsak
(
    nav_rinasak_uuid    uuid primary key references nav_rinasak (nav_rinasak_uuid),
    type                varchar(100) not null,
    tema                varchar(100) not null,
    system              varchar(100),
    nr                  varchar(100),
    fnr                 varchar(100) not null,
    opprettet_bruker    varchar(100) not null,
    opprettet_tidspunkt timestamp    not null,
    endret_bruker       varchar(100),
    endret_tidspunkt    timestamp
);
