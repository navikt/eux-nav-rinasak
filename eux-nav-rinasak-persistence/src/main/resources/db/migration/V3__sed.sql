CREATE TABLE sed
(
    sed_uuid         uuid primary key,
    id               varchar(31),
    nav_rinasak_uuid uuid references nav_rinasak (nav_rinasak_uuid),
    dokument_info_id varchar(100),
    type             varchar(100),
    opprettet_bruker varchar(100),
    opprettet_dato   timestamp
);
