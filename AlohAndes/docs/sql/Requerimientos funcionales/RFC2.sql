SELECT IDALOJAMIENTO
FROM (SELECT IDALOJAMIENTO, COUNT(ISIS2304A461810.CONTRATOS.IDCONTRATO)
      FROM ISIS2304A461810.CONTRATOS
      ORDER BY COUNT(ISIS2304A461810.CONTRATOS.IDCONTRATO))
WHERE ROWNUM <=20;