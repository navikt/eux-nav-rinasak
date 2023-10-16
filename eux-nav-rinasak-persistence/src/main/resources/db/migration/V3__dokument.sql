CREATE TABLE dokument
(
    dokument_uuid    uuid primary key,
    dokument_info_id varchar(100),
    sed_id           uuid,
    sed_versjon      int,
    sed_type         varchar(100),
    nav_rinasak_uuid uuid references nav_rinasak (nav_rinasak_uuid),
    opprettet_bruker varchar(100),
    opprettet_dato   timestamp
);

CREATE INDEX idx_dokument_nav_rinasak_uuid_id ON nav_rinasak (nav_rinasak_uuid);
