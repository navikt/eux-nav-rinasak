CREATE TABLE nav_rinasak
(
    nav_rinasak_uuid       uuid primary key,
    rinasak_id             integer,
    overstyrt_enhetsnummer varchar(31),
    opprettet_bruker       varchar(100),
    opprettet_dato         timestamp
);

CREATE INDEX idx_nav_rinasak_rinasak_id ON nav_rinasak (rinasak_id);
