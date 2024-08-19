CREATE TABLE IF NOT EXISTS OFX_USER (
   ID BIGSERIAL NOT NULL PRIMARY keY,
   NAME VARCHAR(100) NOT null,
   EMAIL VARCHAR(150) not null,
   ID_KEYCLOAK VARCHAR(100),
   STATUS VARCHAR(10) not null default 'ACTIVE',
   created_at timestamp without time zone not null
);