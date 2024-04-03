ALTER TABLE sed_journalstatus
    ADD COLUMN rinasak_id integer not null default 0;
CREATE INDEX ON sed_journalstatus (rinasak_id);
