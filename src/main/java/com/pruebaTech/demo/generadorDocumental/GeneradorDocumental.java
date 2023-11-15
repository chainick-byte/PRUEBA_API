/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.generadorDocumental;

import com.pruebaTech.demo.DTO.ErroresResponse;
import com.pruebaTech.demo.modelos.Libro;
import com.pruebaTech.demo.modelos.Tarea;
import com.pruebaTech.demo.service.ResumenFinalService;
import com.pruebaTech.demo.utils.Constantes;
import com.pruebaTech.demo.utils.GeneradorUUID;
import com.pruebaTech.demo.utils.Utils;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author igorr
 */
@Service
public class GeneradorDocumental {

    private final Logger logger = LoggerFactory.getLogger(GeneradorDocumental.class);
    Utils util = new Utils();
    @Autowired
    private ResumenFinalService resumenFinalService;

    public ErroresResponse generadorCSVCargaMasiva(String idTarea, int prefijo, List<Libro> librosOrdenados) {
        logger.info("}}}===> comienza generadorCSVCargaMasiva");
        ErroresResponse erroresResponse = null;
        FileWriter fw = null;
        String filePath = "";
        logger.error("generadorCSVCargaMasiva : idTarea = " + idTarea);
        try {
            filePath = generarPath(idTarea, prefijo, erroresResponse, Constantes.NOMBRE_ARCHIVO_INSERT);
            if (filePath != null) {

                fw = new FileWriter(filePath);
                fw.append(Constantes.CABECERA_CSV_INSERT);
                fw.append(Constantes.SALTO_LINEA);
                nextLineaInsert(fw, librosOrdenados, idTarea);

            }
            return erroresResponse;
        } catch (IOException e) {
            erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.ERROR, Constantes.ERROR_010009, Constantes.ERROR_GENERADOR_ARCHIVO_MASIVA + e.getMessage());
            logger.error("Error al cerrar el FileWriter: " + e.getMessage());
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                            Constantes.ERROR, Constantes.ERROR_010008, 
                            Constantes.ERROR_GENERADOR_RESUMEN + e.getMessage());
                    logger.error("Error al cerrar el FileWriter: " + e.getMessage());
                }
            }
        }
        return erroresResponse;
    }

    public ErroresResponse generadorCSV(String idTarea, List<Libro> librosOrdenados) {
        logger.info("}}}===> comienza generadorCSV");
        ErroresResponse erroresResponse = null;
        FileWriter fw = null;
        String filePath = "";
        try {
            filePath = generarPath(idTarea, Constantes.SIN_PREFIJO, erroresResponse, Constantes.NOMBRE_ARCHIVO);
            if (filePath != null) {

                fw = new FileWriter(filePath);
                fw.append(Constantes.CABECERA_CSV);
                fw.append(Constantes.SALTO_LINEA);
                nextLinea(fw, librosOrdenados);

            }
            logger.info("}}}===> final generadorCSV");
            return erroresResponse;
        } catch (IOException e) {
            erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.ERROR, Constantes.ERROR_010010, Constantes.ERROR_ELABORACION_ARCHIVO_REQUIRIDO + e.getMessage());
            logger.error("}}}===> error generadorCSV :" + e.getMessage());
            // Manejar la excepción según tus necesidades
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                            Constantes.ERROR, Constantes.ERROR_010008, 
                            Constantes.ERROR_GENERADOR_RESUMEN + e.getMessage());
                    logger.error("Error al cerrar el FileWriter: " + e.getMessage());
                }
            }
        }
        return erroresResponse;
    }

    public ErroresResponse generadorResumen(Tarea tarea, List<Libro> librosOrdenados) {
        logger.info("}}}===> comienza generadorResumen");
        ErroresResponse erroresResponse = null;
        FileWriter fw = null;
        String filePath = "";
        logger.info("generadorResumen : idTarea = " + tarea.getIdTarea());
        try {
            filePath = generarPath(tarea.getIdTarea(),Constantes.SIN_PREFIJO, erroresResponse, Constantes.NOMBRE_ARCHIVO_RESUMEN_FINAL);
            if (filePath != null) {

                fw = new FileWriter(filePath);
                fw = crearCabeceraResumen(fw);
                nextLineConteos(fw, librosOrdenados,tarea);

            }
            logger.info("}}}===> final generadorResumen");
            return erroresResponse;
        } catch (IOException e) {
            erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.ERROR, Constantes.ERROR_010007, Constantes.ERROR_GENERADOR_RESUMEN + e.getMessage());
            logger.error("}}}===> error generadorCSV :" + e.getMessage());
            // Manejar la excepción según tus necesidades
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                            Constantes.ERROR, Constantes.ERROR_010008, 
                            Constantes.ERROR_GENERADOR_RESUMEN + e.getMessage());
                    logger.error("Error al cerrar el FileWriter: " + e.getMessage());
                }
            }
        }
        return erroresResponse;
    }

    private void nextLineaInsert(FileWriter fw, List<Libro> librosOrdenados, String idTarea) throws IOException {
        librosOrdenados.forEach(libro -> {
            try {
                String cadenaFormateada = String.format(
                        Constantes.PATRON_LIBRO_CON_SALTO_DE_LINEA_INSERT,
                        obtenerValor(new GeneradorUUID().uuid()), obtenerValor(libro.getCountry()),
                        obtenerValor(this.util.convertirDatetoFecha(libro, libro.getDate(),
                                Constantes.PATTERN_FECHA_BBDD_STRING)), obtenerValor(libro.getErrorCOnversion()),
                        obtenerValor(libro.getEstadoRegistro()), obtenerValor(libro.getItemType()),
                        obtenerValor(libro.getOrderId()), obtenerValor(libro.getOrderUuid()),
                        obtenerValor(libro.getPriority()), obtenerValor(libro.getRegion()),
                        obtenerValor(libro.getSalesChannel()),
                        obtenerValor(this.util.convertirDatetoFecha(libro, libro.getShipDate(), Constantes.PATTERN_FECHA_BBDD_STRING)),
                        obtenerValor(libro.getTotalCosts()), obtenerValor(libro.getTotalProfits()),
                        obtenerValor(libro.getTotalRevenue()), obtenerValor(libro.getUnitCost()),
                        obtenerValor(libro.getUnitPrice()), obtenerValor(libro.getUnitsSold()), idTarea
                );

                fw.append(cadenaFormateada);
            } catch (IOException ex) {
                logger.error("Error al ejecutar nextLine IOException: " + ex.getMessage());
            } catch (Exception e) {
                logger.error("Error al ejecutar nextLine Exception: " + e.getMessage());
            }
        });
    }

    private void nextLinea(FileWriter fw, List<Libro> librosOrdenados) throws IOException {
        librosOrdenados.forEach(libro -> {
            try {
                String cadenaFormateada = String.format(
                        Constantes.PATRON_LIBRO_CON_SALTO_DE_LINEA,
                        obtenerValor(libro.getOrderId()), obtenerValor(libro.getPriority()),
                        obtenerValor(libro.getDate()), obtenerValor(libro.getRegion()),
                        obtenerValor(libro.getCountry()), obtenerValor(libro.getItemType()),
                        obtenerValor(libro.getSalesChannel()), obtenerValor(libro.getShipDate()),
                        obtenerValor(libro.getUnitsSold()), obtenerValor(libro.getUnitPrice()),
                        obtenerValor(libro.getUnitCost()), obtenerValor(libro.getTotalRevenue()),
                        obtenerValor(libro.getTotalCosts()), obtenerValor(libro.getTotalProfits()));

                fw.append(cadenaFormateada);
            } catch (IOException ex) {
                logger.error("Error al ejecutar nexline IOException: " + ex.getMessage());
            } catch (Exception e) {
                logger.error("Error al ejecutar nexline Exception: " + e.getMessage());
            }

        });
    }

    public String generarPath(String idTarea, int prefijo, ErroresResponse erroresResponse, String nombreArchivo) {
        String fileName = "";
        StringBuilder sb = new StringBuilder();
        try {
            if (prefijo == Constantes.SIN_PREFIJO) {
                logger.info("Genero path sin el prefijo: " + prefijo);
                sb.append(Constantes.ARCHIVO_URL);
                sb.append(nombreArchivo);
                sb.append(idTarea);
                sb.append(Constantes.EXTENSION_ARCHIVO);
                
            } else {
                logger.info("Genero path con el prefijo: " + prefijo);
                
                sb.append(Constantes.ARCHIVO_URL);
                sb.append(prefijo);
                sb.append(Constantes.BARRA_BAJA);
                sb.append(nombreArchivo);
                sb.append(idTarea);
                sb.append(Constantes.EXTENSION_ARCHIVO);
            }
            return sb.toString();
        } catch (Exception e) {
            if (erroresResponse == null ){
                erroresResponse = new ErroresResponse(Constantes.STATUS_KOK, 
                        Constantes.ERROR, Constantes.ERROR_000101, 
                        Constantes.NO_SE_HA_FORMADO_PATH + e.getMessage());
            }
            logger.error("Error al cerrar el generarPath: " + e.getMessage());
        }
        return sb.toString();
    }

    private static String obtenerValor(Object valor) {
        if (valor == null) {
            return "null";
        } else if (valor instanceof BigDecimal) {
            return ((BigDecimal) valor).toPlainString();
        } else if (valor instanceof Double) {
            String valorFormateado = String.format("%.2f", valor);
            return valorFormateado.replace(",", ".");
        } else if (valor instanceof Long) {
            return String.format("%d", valor);
        } else if (valor instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.PATTERN_IMPRISION);
            return dateFormat.format((Date) valor);
        } else {
            return valor.toString();
        }
    }

    private FileWriter crearCabeceraResumen(FileWriter fw) {
        logger.info("creacion de cabecera de resumen: " );
        try {
            fw.append(Constantes.SALTO_LINEA);
            fw.append(Constantes.SALTO_LINEA);
            fw.append(Constantes.SEPARADOR_LINEA);
            fw.append(Constantes.SALTO_LINEA);
            fw.append(Constantes.TABULACION);
            fw.append(Constantes.TABULACION);
            fw.append(Constantes.TABULACION);
            fw.append(Constantes.TABULACION);
            fw.append(Constantes.CABECERA_RESUMEN_FINAL);
            fw.append(Constantes.SALTO_LINEA);
            fw.append(Constantes.SEPARADOR_LINEA);
            fw.append(Constantes.SALTO_LINEA);
             logger.info("cabecera de resumen creada : " );
            return fw;
        } catch (IOException ex) {
            logger.error("Error al ejecutar crearCabeceraResumen: " + ex.getMessage());
        }
        return fw;
    }

    private void nextLineConteos(FileWriter fw, List<Libro> librosOrdenados, Tarea tarea) throws IOException {
        logger.info("cominezo de nextLineConteos: ");
        Map<String, Map<String, Long>> map = contarTiposUnicos(librosOrdenados);
        for (Map.Entry<String, Map<String, Long>> entry : map.entrySet()) {
            fw.write(Constantes.TABULACION + entry.getKey() + Constantes.SALTO_LINEA);
            
            for (Map.Entry<String, Long> subEntry : entry.getValue().entrySet()) {
                fw.write(Constantes.TABULACION
                        + Constantes.TABULACION + Constantes.TABULACION + 
                        subEntry.getKey() + " : " + subEntry.getValue() + Constantes.SALTO_LINEA);
            }

            fw.write(Constantes.SEPARADOR_LINEA + Constantes.SALTO_LINEA);
        }
        logger.info("final de nextLineConteos: ");
        resumenFinalService.guardarMap(map, tarea);
    }

    private Map<String, Map<String, Long>> contarTiposUnicos(List<Libro> libros) {
        Function<Libro, String> mapperRegion = Libro::getRegion;
        Function<Libro, String> mapperCountry = Libro::getCountry;
        Function<Libro, String> mapperItemType = Libro::getItemType;
        Function<Libro, String> mapperSalesChannel = Libro::getSalesChannel;
        Function<Libro, String> mapperOrderPriority = Libro::getPriority;

        Map<String, Long> tiposUnicosRegion = contarTiposUnicos(libros, mapperRegion);
        Map<String, Long> tiposUnicosCountry = contarTiposUnicos(libros, mapperCountry);
        Map<String, Long> tiposUnicosItemType = contarTiposUnicos(libros, mapperItemType);
        Map<String, Long> tiposUnicosSalesChannel = contarTiposUnicos(libros, mapperSalesChannel);
        Map<String, Long> tiposUnicosOrderPriority = contarTiposUnicos(libros, mapperOrderPriority);

        return Map.of(
                Constantes.REGION, tiposUnicosRegion,
                Constantes.COUNTRY, tiposUnicosCountry,
                Constantes.ITEM_TYPE, tiposUnicosItemType,
                Constantes.SALES_CHANNEL, tiposUnicosSalesChannel,
                Constantes.ORDER_PRIORITY, tiposUnicosOrderPriority
        );
    }

    private Map<String, Long> contarTiposUnicos(List<Libro> libros, Function<Libro, String> mapper) {
        return libros.stream()
                .map(mapper)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

}
