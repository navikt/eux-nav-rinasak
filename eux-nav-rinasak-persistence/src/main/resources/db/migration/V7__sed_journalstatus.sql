CREATE TABLE sed_journalstatus
(
    sed_journalstatus_uuid uuid primary key,
    status                 text      not null,
    sed_id                 uuid      not null,
    sed_versjon            int       not null,
    opprettet_bruker       text      not null,
    opprettet_tidspunkt    timestamp not null,
    endret_bruker          text      not null,
    endret_tidspunkt       timestamp not null,
    UNIQUE (sed_id, sed_versjon)
);
CREATE INDEX ON sed_journalstatus (sed_id);
