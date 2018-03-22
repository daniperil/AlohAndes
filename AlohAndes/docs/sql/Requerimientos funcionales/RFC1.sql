SELECT SUM(COSTOTOTAL), IDOPERADOR

FROM ISIS2304A461810.CONTRATOS 
WHERE FECHAINICIO LIKE '%/18'
GROUP BY IDOPERADOR;
------------------------------------------------
-- Se asume que el pago se hace al inicio
------------------------------------------------