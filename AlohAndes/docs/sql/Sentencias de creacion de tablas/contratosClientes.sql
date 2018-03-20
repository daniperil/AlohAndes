--------------------------------------------------------
-- Archivo creado  - martes-marzo-20-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CONTRATOSCLIENTES
--------------------------------------------------------

  CREATE TABLE "ISIS2304A461810"."CONTRATOSCLIENTES" 
   (	"IDCONTRATO" NUMBER, 
	"IDCLIENTE" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS NOLOGGING
  TABLESPACE "TBSPROD" ;
--------------------------------------------------------
--  DDL for Index CONTRATOSCLIENTES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ISIS2304A461810"."CONTRATOSCLIENTES_PK" ON "ISIS2304A461810"."CONTRATOSCLIENTES" ("IDCONTRATO", "IDCLIENTE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING 
  TABLESPACE "TBSPROD" ;
--------------------------------------------------------
--  Constraints for Table CONTRATOSCLIENTES
--------------------------------------------------------

  ALTER TABLE "ISIS2304A461810"."CONTRATOSCLIENTES" MODIFY ("IDCLIENTE" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A461810"."CONTRATOSCLIENTES" MODIFY ("IDCONTRATO" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A461810"."CONTRATOSCLIENTES" ADD CONSTRAINT "CONTRATOSCLIENTES_PK" PRIMARY KEY ("IDCONTRATO", "IDCLIENTE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING 
  TABLESPACE "TBSPROD"  ENABLE;
