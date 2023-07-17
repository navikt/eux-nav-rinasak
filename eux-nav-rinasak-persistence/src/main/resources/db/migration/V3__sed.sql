CREATE TABLE sed
(
    id               varchar(31) primary key,
    rinasak_id       varchar(31) references nav_rinasak (rinasak_id),
    opprettet_bruker varchar(100),
    opprettet_dato   timestamp
);
