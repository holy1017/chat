/* Formatted on 2015/08/31 오후 2:14:04 (QP5 v5.227.12220.39754) */
CREATE SEQUENCE "CHAT_USER_SQ"
   MINVALUE 1
   MAXVALUE 999999999
   INCREMENT BY 1
   START WITH 1
   CACHE 20
   NOORDER
   NOCYCLE;

CREATE TABLE "CHAT_USER"
(
   "ID"       VARCHAR2 (20 BYTE) NOT NULL ENABLE,
   "PW"       VARCHAR2 (64 BYTE) NOT NULL ENABLE,
   "NICK"     VARCHAR2 (20 BYTE) NOT NULL ENABLE,
   "NO"       NUMBER NOT NULL ENABLE,
   "GENDER"   NUMBER (1, 0) DEFAULT 0 NOT NULL ENABLE,
   "INDATE"   DATE DEFAULT SYSDATE NOT NULL ENABLE,
   CONSTRAINT "TABLE1_PK_ID" PRIMARY KEY
      ("ID")
      USING INDEX PCTFREE 10
                  INITRANS 2
                  MAXTRANS 255
                  COMPUTE STATISTICS
                  STORAGE (INITIAL 65536
                           NEXT 1048576
                           MINEXTENTS 1
                           MAXEXTENTS 2147483645
                           PCTINCREASE 0
                           FREELISTS 1
                           FREELIST GROUPS 1
                           BUFFER_POOL DEFAULT
                           FLASH_CACHE DEFAULT
                           CELL_FLASH_CACHE DEFAULT)
                  TABLESPACE "USERS"
      ENABLE,
   CONSTRAINT "CHAT_USER_UK_NICK" UNIQUE
      ("NICK")
      USING INDEX PCTFREE 10
                  INITRANS 2
                  MAXTRANS 255
                  COMPUTE STATISTICS
                  STORAGE (INITIAL 65536
                           NEXT 1048576
                           MINEXTENTS 1
                           MAXEXTENTS 2147483645
                           PCTINCREASE 0
                           FREELISTS 1
                           FREELIST GROUPS 1
                           BUFFER_POOL DEFAULT
                           FLASH_CACHE DEFAULT
                           CELL_FLASH_CACHE DEFAULT)
                  TABLESPACE "USERS"
      ENABLE,
   CONSTRAINT "CHAT_USER_UK_NO" UNIQUE
      ("NO")
      USING INDEX PCTFREE 10
                  INITRANS 2
                  MAXTRANS 255
                  COMPUTE STATISTICS
                  STORAGE (INITIAL 65536
                           NEXT 1048576
                           MINEXTENTS 1
                           MAXEXTENTS 2147483645
                           PCTINCREASE 0
                           FREELISTS 1
                           FREELIST GROUPS 1
                           BUFFER_POOL DEFAULT
                           FLASH_CACHE DEFAULT
                           CELL_FLASH_CACHE DEFAULT)
                  TABLESPACE "USERS"
      ENABLE
)
SEGMENT CREATION IMMEDIATE
PCTFREE 10
PCTUSED 40
INITRANS 1
MAXTRANS 255
NOCOMPRESS
LOGGING
STORAGE (INITIAL 65536
         NEXT 1048576
         MINEXTENTS 1
         MAXEXTENTS 2147483645
         PCTINCREASE 0
         FREELISTS 1
         FREELIST GROUPS 1
         BUFFER_POOL DEFAULT
         FLASH_CACHE DEFAULT
         CELL_FLASH_CACHE DEFAULT)
TABLESPACE "USERS";


COMMENT ON COLUMN "CHAT_USER"."GENDER" IS '1:남,2:여';

COMMENT ON COLUMN "CHAT_USER"."INDATE" IS '가입날자';


CREATE OR REPLACE TRIGGER "CHAT_USER_TRIGGER_NO"
   BEFORE INSERT
   ON chat_user
   FOR EACH ROW
   -- Optionally restrict this trigger to fire only when really needed
   WHEN (new.no IS NULL)
DECLARE
   v_id   chat_user.no%TYPE;
BEGIN
   -- Select a new value from the sequence into a local variable. As David
   -- commented, this step is optional. You can directly select into :new.qname_id
   SELECT chat_user_sq.NEXTVAL INTO v_id FROM DUAL;

   -- :new references the record that you are about to insert into qname. Hence,
   -- you can overwrite the value of :new.qname_id (qname.qname_id) with the value
   -- obtained from your sequence, before inserting
   :new.no := v_id;
END chat_user_TRIGGER_no;
/

ALTER TRIGGER "CHAT_USER_TRIGGER_NO" ENABLE;