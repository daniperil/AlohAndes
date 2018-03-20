--------------------------------------------------------
-- Archivo creado  - martes-marzo-20-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table SERVICIOSOFRECIDOS
--------------------------------------------------------

  CREATE TABLE "ISIS2304A461810"."SERVICIOSOFRECIDOS" 
   (	"IDSERVICIO" NUMBER, 
	"IDALOJAMIENTO" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS NOLOGGING
  TABLESPACE "TBSPROD" ;
--------------------------------------------------------
--  DDL for Index SERVICIOSVIVIENDAS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ISIS2304A461810"."SERVICIOSVIVIENDAS_PK" ON "ISIS2304A461810"."SERVICIOSOFRECIDOS" ("IDSERVICIO", "IDALOJAMIENTO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING 
  TABLESPACE "TBSPROD" ;
--------------------------------------------------------
--  Constraints for Table SERVICIOSOFRECIDOS
--------------------------------------------------------

  ALTER TABLE "ISIS2304A461810"."SERVICIOSOFRECIDOS" MODIFY ("IDALOJAMIENTO" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A461810"."SERVICIOSOFRECIDOS" MODIFY ("IDSERVICIO" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A461810"."SERVICIOSOFRECIDOS" ADD CONSTRAINT "SERVICIOSVIVIENDAS_PK" PRIMARY KEY ("IDSERVICIO", "IDALOJAMIENTO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 NOLOGGING 
  TABLESPACE "TBSPROD"  ENABLE;
