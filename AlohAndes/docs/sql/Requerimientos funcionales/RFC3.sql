SELECT IDALOJAMIENTO, NOMBRE
FROM ISIS2304A461810.ALOJAMIENTOS
WHERE (IDALOJAMIENTO NOT IN (SELECT IDALOJAMIENTO
                            FROM ISIS2304A461810.ALOJAMIENTOS 
                                 NATURAL INNER JOIN 
                                 ISIS2304A461810.CONTRATOS
                            WHERE (FECHAINICIO BETWEEN F1 AND F2)OR (FECHAFIN BETWEEN F1 AND F2))
      AND IDALOJAMIENTO IN (SELECT IDALOJAMIENTO
                             FROM ((ISIS2304A461810.SERVICIOSOFRECIDOS)
                                    NATURAL INNER JOIN 
                                   (ISIS2304A461810.SERVICIOS))
                             WHERE DESCRIPCION =  S1)
      AND IDALOJAMIENTO IN (SELECT IDALOJAMIENTO
                             FROM ISIS2304A461810.SERVICIOSOFRECIDOS
                                 NATURAL INNER JOIN 
                                 ISIS2304A461810.SERVICIOS
                            WHERE DESCRIPCION =  S2));
--------------------------------------------------------
-- Esta sentencia se escribió teniendo en mente que en el programa se ingresarían como parámetros la fecha inicial del rango F1, la fecha final del rango F2, y los N 
--servicios que se deseen S1,...,SN, para los cuales se repite la última parte de la sentencia
--------------------------------------------------------