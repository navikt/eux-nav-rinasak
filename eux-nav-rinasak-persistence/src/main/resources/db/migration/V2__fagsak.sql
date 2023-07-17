CREATE TABLE fagsak
(
    id               varchar(100) primary key,
    tema             varchar(100),
    system           varchar(100),
    nr               varchar(100),
    type             varchar(100),
    rinasak_id       varchar(100) references nav_rinasak(rinasak_id),
    opprettet_bruker varchar(100),
    opprettet_dato   timestamp
);
