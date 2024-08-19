CREATE TABLE IF NOT EXISTS USER_UE_INVITE (
   ID BIGSERIAL NOT NULL PRIMARY KEY,
   email varchar NOT NULL,
   ID_UE BIGINT NOT NULL REFERENCES UNIDADE_ECONOMICA(ID),
   ADMINISTRATOR BOOLEAN NOT NULL DEFAULT FALSE,
   CAN_READ BOOLEAN NOT NULL DEFAULT FALSE,
   CAN_WRITE BOOLEAN NOT NULL DEFAULT FALSE,
   CAN_IMPORT BOOLEAN NOT NULL DEFAULT FALSE,
   STATUS VARCHAR(10) not null default 'INVITED',
   CREATED_BY BIGINT NOT NULL REFERENCES OFX_USER(ID),
   created_at timestamp without time zone not null
);