CREATE TABLE dokument
(
    dokument_uuid       uuid primary key,
    dokument_info_id    varchar(100),
    sed_id              uuid         not null,
    sed_versjon         int          not null,
    sed_type            varchar(100) not null,
    nav_rinasak_uuid    uuid references nav_rinasak (nav_rinasak_uuid),
    opprettet_bruker    varchar(100) not null,
    opprettet_tidspunkt timestamp    not null
);

CREATE INDEX idx_dokument_sed_id ON dokument (sed_id);
CREATE INDEX idx_dokument_nav_rinasak_uuid_id ON dokument (nav_rinasak_uuid);
