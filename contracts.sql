
--------------------------------------------------------
--  DDL for Sequence SEQ_ERRORLOG
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_ERRORLOG"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

--------------------------------------------------------
--  DDL for Sequence SEQ_BIDS
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_BIDS"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Table ERRORLOG
--------------------------------------------------------

  CREATE TABLE "ERRORLOG" 
   ("CTRERRORLOG" NUMBER, 
	"TXTERRORMSG" VARCHAR2(1024 BYTE), 
	"DTEERRORLOG" DATE, 
	"TXTLOCATION" VARCHAR2(64 BYTE), 
	"TXTPARAMS" VARCHAR2(64 BYTE), 
	"TXTUSER" VARCHAR2(20 BYTE)
   );
 

   COMMENT ON COLUMN "ERRORLOG"."CTRERRORLOG" IS 'Primary key';
 
   COMMENT ON COLUMN "ERRORLOG"."TXTERRORMSG" IS 'Error message';
 
   COMMENT ON COLUMN "ERRORLOG"."DTEERRORLOG" IS 'Date of error';
 
   COMMENT ON COLUMN "ERRORLOG"."TXTLOCATION" IS 'Location';
 
   COMMENT ON COLUMN "ERRORLOG"."TXTPARAMS" IS 'Parameter values';
 
   COMMENT ON COLUMN "ERRORLOG"."TXTUSER" IS 'User';
 
   COMMENT ON TABLE "ERRORLOG"  IS 'Application error log';

--------------------------------------------------------
--  Constraints for Table ERRORLOG
--------------------------------------------------------

  ALTER TABLE "ERRORLOG" MODIFY ("CTRERRORLOG" NOT NULL ENABLE); 

--------------------------------------------------------
--  DDL for Table CONTRACTS
--------------------------------------------------------

  CREATE TABLE "CONTRACTS" 
    ("CONTRACTID" VARCHAR2(15),
     "DESTCITY" VARCHAR2(15),
     "ORIGCITY" VARCHAR2(15),
     "ORDERITEM" VARCHAR2(15)
   );
   
--------------------------------------------------------
--  DDL for Index CONTRACTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CONTRACTS_PK" ON "CONTRACTS" ("CONTRACTID");
--------------------------------------------------------
--  Constraints for Table CONTRACTS
--------------------------------------------------------

  ALTER TABLE "CONTRACTS" ADD CONSTRAINT "CONTRACTS_DEST_NN" CHECK (destcity IS NOT NULL) ENABLE;
 
  ALTER TABLE "CONTRACTS" ADD CONSTRAINT "CONTRACTS_ITEM_NN" CHECK (orderitem IS NOT NULL) ENABLE;
 
  ALTER TABLE "CONTRACTS" ADD CONSTRAINT "CONTRACTS_ORIG_NN" CHECK (origcity IS NOT NULL) ENABLE;
 
  ALTER TABLE "CONTRACTS" ADD CONSTRAINT "CONTRACTS_PK" PRIMARY KEY ("CONTRACTID");

--------------------------------------------------------
--  DDL for Table BIDS
--------------------------------------------------------

  CREATE TABLE "BIDS" 
   ("BIDID" NUMBER(10,0), 
	"CONTRACTID" VARCHAR2(12 BYTE), 
	"BIDDER" VARCHAR2(12 BYTE), 
	"USERNAME" VARCHAR2(12 BYTE), 
	"BIDAMOUNT" NUMBER(6,0), 
	"BIDPLACEDDATE" DATE
   );
--------------------------------------------------------
--  DDL for Index BIDS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BIDS_PK" ON "BIDS" ("BIDID");
  
--------------------------------------------------------
--  DDL for Trigger BID_BEFORE_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BID_BEFORE_INSERT" 
before insert
  on bids
  FOR EACH ROW
declare 
   v_username varchar2(20);
begin
    select user into v_username
    from dual;
    
    :new.bidPlacedDate := sysdate;
    
    :new.username := v_username;
end;
/

ALTER TRIGGER "BID_BEFORE_INSERT" ENABLE;
--------------------------------------------------------
--  Constraints for Table BIDS
--------------------------------------------------------

  ALTER TABLE "BIDS" ADD CONSTRAINT "BIDAMOUNT_CK" CHECK (bidAmount>0 AND bidAmount<10000) ENABLE;
 
  ALTER TABLE "BIDS" ADD CONSTRAINT "BIDDER_NN" CHECK (bidder IS not null) ENABLE;
 
  ALTER TABLE "BIDS" ADD CONSTRAINT "BIDPLACEDDATE_CK" CHECK (bidPlacedDate BETWEEN TO_DATE('2018/03/12', 'YYYY/MM/DD')
                                                       AND TO_DATE('2018/12/12', 'YYYY/MM/DD')) ENABLE;
 
  ALTER TABLE "BIDS" ADD CONSTRAINT "BIDS_CONTRACT_NN" CHECK (contractID IS not null) ENABLE;
 
  ALTER TABLE "BIDS" ADD CONSTRAINT "BIDS_PK" PRIMARY KEY ("BIDID");
 
  ALTER TABLE "BIDS" ADD CONSTRAINT "USERNAME_NN" CHECK (userName IS not null) ENABLE;

--------------------------------------------------------
--  DDL for Package PKGUTIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PKGUTIL" AS 

    PROCEDURE ERRORLOG_ADD( TXTERRORMSG IN VARCHAR2, 
                            TXTLOCATION IN VARCHAR2,
                            TXTPARAMS   IN VARCHAR2,
                            TXTUSER     IN VARCHAR2);
END PKGUTIL;

/

--------------------------------------------------------
--  DDL for Package Body PKGUTIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PKGUTIL" AS 

PROCEDURE ERRORLOG_ADD( TXTERRORMSG IN VARCHAR2, 
                        TXTLOCATION IN VARCHAR2,
                        TXTPARAMS   IN VARCHAR2,
                        TXTUSER     IN VARCHAR2) IS
                          
 BEGIN

        INSERT INTO ERRORLOG (CTRERRORLOG, TXTERRORMSG, DTEERRORLOG,
                                 TXTLOCATION, TXTPARAMS, TXTUSER)
          VALUES (SEQ_ERRORLOG.NEXTVAL, TXTERRORMSG, sysdate,
                  TXTLOCATION, 
                  TXTPARAMS,
                  TXTUSER);

 END;

END PKGUTIL;

/

--------------------------------------------------------
--  DDL for Package PKGREC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PKGREC" AS 

/* Modification History:

   1.0.0   MAR 22, 2018 - Lang - Formatting the procedures.

*/

 TYPE CURSOR_TYPE IS REF CURSOR;

 PROCEDURE CONTRACT_ADD( CONTRACTID IN VARCHAR
                           , ORIGCITY IN VARCHAR
                           , DESTCITY IN VARCHAR
                           , ORDERITEM IN VARCHAR
                           , RESULT OUT NUMBER);

  PROCEDURE CONTRACT_LIST( RESULT_SET OUT CURSOR_TYPE);
    
  PROCEDURE BIDS_ADD( CONTRACTID IN VARCHAR
                    , BIDDER IN VARCHAR
                    , BIDAMOUNT IN NUMBER
                    , RESULT OUT NUMBER );

  PROCEDURE BIDS_LIST ( RESULT_SET OUT CURSOR_TYPE );                    

END PKGREC;

/

--------------------------------------------------------
--  DDL for Package Body PKGREC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PKGREC" AS
/* Modification History:

   1.0.0   MAR 22, 2018 - Lang - Formatting the procedures.

*/

 PROCEDURE CONTRACT_ADD( CONTRACTID IN VARCHAR
                           , ORIGCITY IN VARCHAR
                           , DESTCITY IN VARCHAR
                           , ORDERITEM IN VARCHAR
                           , RESULT OUT NUMBER) IS
--
-- This procedure adds in a new registration record to the CONTRACTS table.
--

   v_code NUMBER;
   v_errm VARCHAR2(1024);
   v_user VARCHAR2(64);
 BEGIN

   RESULT := 0; 

   INSERT INTO CONTRACTS (CONTRACTID, ORIGCITY, DESTCITY, ORDERITEM) 
      VALUES (CONTRACTID, ORIGCITY, DESTCITY, ORDERITEM);


   EXCEPTION
     WHEN OTHERS THEN

        RESULT := -1;

        select user into v_user from dual;

        RESULT := SQLCODE;
        v_errm := SUBSTR(SQLERRM, 1 , 1024);

        PKGUTIL.ERRORLOG_ADD(v_errm, 
                  'PKGREC.CONTRACT_ADD', 
                  'CONTRACT ID=' || CONTRACTID,
                  v_user);

 END;

PROCEDURE CONTRACT_LIST( RESULT_SET OUT CURSOR_TYPE) IS
--
-- This procedure returns a list of all the rec centre names.
--
   v_code NUMBER;
   v_errm VARCHAR2(1024);
   v_user VARCHAR2(64);
BEGIN

   OPEN RESULT_SET FOR
       SELECT CONTRACTID, ORIGCITY, DESTCITY, ORDERITEM
       FROM CONTRACTS;

  EXCEPTION
     WHEN OTHERS THEN     

        select user into v_user from dual;

        v_errm := SUBSTR(SQLERRM, 1 , 1024);

        PKGUTIL.ERRORLOG_ADD(v_errm, 
                  'PKGREC.CONTRACTS_LIST', 
                  ' ',
                  v_user);           

END;

PROCEDURE BIDS_ADD( CONTRACTID IN VARCHAR
                    , BIDDER IN VARCHAR
                    , BIDAMOUNT IN NUMBER
                    , RESULT OUT NUMBER ) IS
--
-- This procedure adds in a new bids record to the BIDS table.
--

   v_code NUMBER;
   v_errm VARCHAR2(1024);
   v_user VARCHAR2(64);
 BEGIN

   RESULT := 0; 
   
   INSERT INTO BIDS (BIDID, CONTRACTID, BIDDER, BIDAMOUNT) 
      VALUES (SEQ_BIDS.NEXTVAL, CONTRACTID, BIDDER, BIDAMOUNT);


   EXCEPTION
     WHEN OTHERS THEN

        RESULT := -1;

        select user into v_user from dual;

        RESULT := SQLCODE;
        v_errm := SUBSTR(SQLERRM, 1 , 1024);

        PKGUTIL.ERRORLOG_ADD(v_errm, 
                  'PKGREC.BIDS_ADD', 
                  'CONTRACT ID=' || CONTRACTID,
                  v_user);

 END;                    

  PROCEDURE BIDS_LIST ( RESULT_SET OUT CURSOR_TYPE ) IS

-- This procedure returns a list of all the bid records.
--
   v_code NUMBER;
   v_errm VARCHAR2(1024);
   v_user VARCHAR2(64);
BEGIN

   OPEN RESULT_SET FOR
       SELECT CONTRACTID, BIDDER, BIDAMOUNT, BIDPLACEDDATE
       FROM BIDS
       ORDER BY CONTRACTID, BIDDER, BIDPLACEDDATE;

  EXCEPTION
     WHEN OTHERS THEN     

        select user into v_user from dual;

        v_errm := SUBSTR(SQLERRM, 1 , 1024);

        PKGUTIL.ERRORLOG_ADD(v_errm, 
                  'PKGREC.BIDS_LIST', 
                  ' ',
                  v_user);           

END;  

END PKGREC;

/

INSERT INTO "CONTRACTS" (CONTRACTID, ORIGCITY, DESTCITY, ORDERITEM) VALUES ('4RDW', 'Victoria', 'Prince George', 'Seaweed');
INSERT INTO "CONTRACTS" (CONTRACTID, ORIGCITY, DESTCITY, ORDERITEM) VALUES ('8EKD', 'Vancouver', 'Victoria', 'Vegetable');
INSERT INTO "CONTRACTS" (CONTRACTID, ORIGCITY, DESTCITY, ORDERITEM) VALUES ('3HQC', 'Seattle', 'Vancouver', 'Pharmaceutical');
COMMIT;
