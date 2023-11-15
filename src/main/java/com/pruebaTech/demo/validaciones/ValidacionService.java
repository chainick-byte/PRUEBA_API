/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.validaciones;

import com.pruebaTech.demo.DTO.ErroresResponse;
import com.pruebaTech.demo.utils.Constantes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 *
 * @author igorr
 */
@Service
public class ValidacionService {

    public ErroresResponse validarUrlEntrada(String url, String procedencia) {
        ErroresResponse erroresResponse = null;

        if (urlNoNUll(url)) {
            erroresResponse = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                    Constantes.ERROR_100003, Constantes.URL_VACIA);
        } else {
            if (url.length() > Constantes.LONGITUD_URL) {
                erroresResponse = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                        Constantes.ERROR_100004, Constantes.URL_LONGITUD);
            }
            if (isValidaUrl(url)) {
                erroresResponse = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                        Constantes.ERROR_100005, Constantes.URL_NO_PATTERN);
            }
            if (Constantes.VER_ORDERS_PAGINADOS.equals(procedencia)) {
                if (validarUrlOrdersEntrada(url)) {
                    erroresResponse = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                            Constantes.ERROR_010002, Constantes.URL_ORDERS_MALA_STRUCTURA);
                }
            }
        }

        return erroresResponse;
    }

    private boolean validarUrlOrdersEntrada(String url) {
        boolean result = false;

        if (!url.contains(Constantes.VALIDACION_URL_ORDERS)) {
            result = true;
        }
        return result;
    }

    private boolean urlNoNUll(String url) {
        boolean result = false;
        if (url == null || url.length() == 0) {
            return true;
        }

        return result;
    }

    private boolean isValidaUrl(String url) {

        boolean result = false;
        String pattern = Constantes.PATTERN_URL;
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(url);
        if (!matcher.matches()) {
            result = true;
            System.out.println("La cadena es una URL válida (http o https).");
        }
        return result;
    }

}
