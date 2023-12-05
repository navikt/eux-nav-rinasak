CREATE TABLE initiell_fagsak
(
    nav_rinasak_uuid    uuid primary key references nav_rinasak (nav_rinasak_uuid),
    id                  varchar(100),
    tema                varchar(100) not null,
    system              varchar(100),
    nr                  varchar(100),
    type                varchar(100) not null,
    arkiv               varchar(100) not null,
    fnr                 varchar(100) not null,
    opprettet_bruker    varchar(100) not null,
    opprettet_tidspunkt timestamp    not null
);
