CREATE TABLE sed
(
    id               varchar(31) primary key,
    nav_rinasak_uuid uuid references nav_rinasak (nav_rinasak_uuid),
    opprettet_bruker varchar(100),
    opprettet_dato   timestamp
);
