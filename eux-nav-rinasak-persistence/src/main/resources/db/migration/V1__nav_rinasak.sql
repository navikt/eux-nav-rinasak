CREATE TABLE nav_rinasak
(
    nav_rinasak_uuid       uuid primary key,
    rinasak_id             integer      not null,
    overstyrt_enhetsnummer varchar(31),
    opprettet_bruker       varchar(100) not null,
    opprettet_tidspunkt    timestamp    not null
);
CREATE UNIQUE INDEX idx_nav_rinasak_rinasak_id ON nav_rinasak (rinasak_id);
