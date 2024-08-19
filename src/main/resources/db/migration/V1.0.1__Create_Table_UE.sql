CREATE TABLE IF NOT EXISTS UNIDADE_ECONOMICA (
   ID BIGSERIAL NOT NULL PRIMARY KEY,
   NAME VARCHAR(100) NOT NULL,
   STATUS VARCHAR(10) not null default 'ACTIVE',
   created_at timestamp without time zone not null
);