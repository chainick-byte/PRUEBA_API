/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.utils;

import com.pruebaTech.demo.modelos.Libro;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author igorr
 */
public class Utils {

    private final Logger logger = LoggerFactory.getLogger(Utils.class);

    public String convertirDatetoFecha(Libro libro, Date date, String pattern) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(date);
        } catch (ParseException e) {
            addErrorNuevo(libro, Constantes.ERROR_001002);
            logger.error("conversion" + e.getMessage());
        }
        return null;
    }

    public Date conervitStringToFecha(Libro libro, String fecha, String pattern) throws java.text.ParseException {
        Date date = null;
        if (fecha != null) {
            try {
                SimpleDateFormat originalFormat = new SimpleDateFormat(Constantes.ORIGINAL_FORMAT_FECHA);
                SimpleDateFormat targetFormat = new SimpleDateFormat(pattern);

                Pattern singleDigitDayMonth = Pattern.compile(Constantes.PATTERN_FECHAS_CON_DIGITOS_OK);
                Matcher matcher = singleDigitDayMonth.matcher(fecha);

                if (matcher.matches()) {
                    String modifiedDate = String.format("%02d/%02d/%s",
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            matcher.group(3));
                    logger.error("=====>conversion fecha : " + fecha);
                    fecha = modifiedDate;
                    
                    logger.error("<====conversion modifiedDate : " + modifiedDate);
                }
                
                date = originalFormat.parse(fecha);
                String formattedDate = targetFormat.format(date);

                return targetFormat.parse(formattedDate);
            } catch (ParseException e) {
                addErrorNuevo(libro, Constantes.ERROR_001002);
                logger.error("conversion" + e.getMessage());
            }
            return date;
        }
        addErrorNuevo(libro, Constantes.ERROR_001002);
        return date;
    }

    public Long convertirStringToLong(Libro libro, String stringEntrada) {

        try {
            long result = Long.parseLong(stringEntrada);
            return result;
        } catch (NumberFormatException e) {
            addErrorNuevo(libro, Constantes.ERROR_001001);
            logger.error("conversion" + e.getMessage());
        }
        return null;
    }

    public BigDecimal convertirStringToBigDecimal(Libro libro, String stringEntrada) {
        try {
            BigDecimal result = new BigDecimal(stringEntrada);
            return result;
        } catch (NumberFormatException e) {
            addErrorNuevo(libro, Constantes.ERROR_001003);
            logger.error("conversion" + e.getMessage());
        }
        return null;
    }

    public void addErrorNuevo(Libro libro, String error) {
        String erroresExistentes = libro.getErrorCOnversion() != null
                ? libro.getErrorCOnversion() : Constantes.ERROR_COMIENZO;
        StringBuilder sb = new StringBuilder();
        sb.append(erroresExistentes);
        sb.append(error);
        sb.append(Constantes.ERROR_SEPARADOR);
        libro.setErrorCOnversion(sb.toString());
        libro.setEstadoRegistro(Constantes.ESTADO_NO_ACTIVO);
    }

}
