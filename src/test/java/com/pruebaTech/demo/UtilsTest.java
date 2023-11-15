/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo;

import com.pruebaTech.demo.configuration.TestingConfig;
import com.pruebaTech.demo.modelos.Libro;
import com.pruebaTech.demo.utils.Constantes;
import com.pruebaTech.demo.utils.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.LoggerFactory;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author igorr
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestingConfig.class)
public class UtilsTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UtilsTest.class);

    Utils util = new Utils();
    String fechaOK = "7/27/2012";
    String fechaFotmatoSinDigitos = "1/1/2022";
    String fechaNull = null;

    @Test
    public void testConvertirDatetoFecha_FechaOK() throws ParseException {
        Libro libro = new Libro();
        Date resultado = util.conervitStringToFecha(libro, fechaOK, Constantes.DESEADO_FORMATO_FECHA);
        assertNotNull(resultado);
        assertTrue(libro.getEstadoRegistro()== Constantes.ESTADO_ACTIVO);
        logger.info("fechaOK validada");
    }

    @Test
    public void testConvertirDatetoFecha_FechaFotmatoSinDigitos() throws ParseException {
        Libro libro = new Libro();

        Date resultado = util.conervitStringToFecha(libro, fechaFotmatoSinDigitos, Constantes.DESEADO_FORMATO_FECHA);

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DESEADO_FORMATO_FECHA);
        String fechaEsperada = "2022-01-01";
        String fechaObtenida = sdf.format(resultado);

        assertEquals(fechaEsperada, fechaObtenida);
        assertTrue(libro.getEstadoRegistro()== Constantes.ESTADO_ACTIVO);
        logger.info("fechaFotmatoSinDigitos validada");
    }

    @Test
    public void testConvertirDatetoFecha_FechaNull() throws ParseException {
        Libro libro = new Libro();
        Date resultado = util.conervitStringToFecha(libro, fechaNull, Constantes.DESEADO_FORMATO_FECHA);
        assertTrue(libro.getErrorCOnversion().contains(Constantes.ERROR_001002));
        assertTrue(libro.getEstadoRegistro()== Constantes.ESTADO_NO_ACTIVO);
        assertNull(resultado);
        logger.info("fechaNull validada");
    }
    
   

}
