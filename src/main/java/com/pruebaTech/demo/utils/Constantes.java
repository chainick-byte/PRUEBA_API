/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.utils;

/**
 *
 * @author igorr
 */
public class Constantes {

    //patterns
    public final static String PATTERN_URL = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public final static String PATTERN_FECHAS_CON_DIGITOS_OK = "(\\d)/(\\d)/(\\d{4})";
    public final static String PATTERN_FECHA = "MM/dd/yyyy";
    public final static String PATTERN_IMPRISION = "dd/MM/yyyy";
    public final static String PATTERN_FECHA_BBDD_STRING = "yyyy-MM-dd HH:mm:ss";
    public final static String ORIGINAL_FORMAT_FECHA = "dd/M/yyyy";
    public final static String DESEADO_FORMATO_FECHA = "yyyy-MM-dd";
    public final static String VACIO = "";
    // vistas 
    public final static String FRAGMENTO = "fragmento";
    public final static String EMPTY = "empty";
    public final static String INDEX = "index";
    public final static String REDIRECT_TO_INDEX = "redirect:/";
    public final static String VER_ORDERS = "orders";
    public final static String VER_DETALLE = "verDetalle";
    public final static String PANTALLA_GUARDAR = "pantallaGuardar";
    //procedencia de validacion especifica
    public final static String VER_ORDERS_PAGINADOS = "orders_paginados";

    // Errores
    public final static String STATUS_OK = "1";
    public final static String STATUS_KOK = "0";
    public final static String ERROR = "Error";
    public final static int MILL = 1000;
    public final static int TAMANNO_PARTICION = 100000;
    public final static int SIN_PREFIJO = -1;
    public final static String NO_ERROR = "No Error";

    // longitud url 
    public static int LONGITUD_URL = 1024;
    //Estado registro
    public final static int ESTADO_ACTIVO = 1;
    public final static int ESTADO_NO_ACTIVO = 0;

    // Errores HTTP
    public final static String HTTP_OK = "200";
    public final static String HTTP_CONTENT_NOF_FOUND = "204";
    public final static int INTEGER_HTTP_OK = 200;
    public final static int INTEGER_HTTP_CONTENT_NOF_FOUND = 204;
    public final static String HTTP_BAD_REQUEST = "400";
    public final static String HTTP_SERVER_ERROR = "500";
    // errores personalizados
    public final static String ERROR_100001 = "10001";
    public final static String ERROR_100002 = "10002";
    public final static String ERROR_100003 = "10003";
    public final static String ERROR_100004 = "10004";
    public final static String ERROR_100005 = "10005";
    public final static String ERROR_000000 = "00000";

    public final static String ERROR_010001 = "01001";
    public final static String ERROR_010002 = "01002";
    public final static String ERROR_010003 = "01003";
    public final static String ERROR_010004 = "01004";
    public final static String ERROR_010005 = "01005";
    public final static String ERROR_010006 = "01006";
    public final static String ERROR_010007 = "01007";
    public final static String ERROR_010008 = "01008";
    public final static String ERROR_010009 = "01009";
    public final static String ERROR_010010 = "01010";
    //errores de conversion
    public final static String ERROR_001001 = "00101";
    public final static String ERROR_001002 = "00102";
    public final static String ERROR_001003 = "00103";
    public final static String ERROR_001004 = "00104";

    // errores de creacion documental
    public final static String ERROR_000101 = "00011";

    public final static String ERROR_SEPARADOR = "|=|";
    public final static String ERROR_COMIENZO = "=>|";
    public final static String BARRA_BAJA = "_";
    // Descripcion 
    public final static String BAD_REQUEST = "Bad request";

    //descripcion personalizada
    public final static String OBJETO_INESPERADO = "El objeto que se espera recibir debe tener campos 'orders' y 'bookmarks'";
    public final static String URL_VACIA = "El campo está vacío. Se esperaba una URL.";
    public final static String URL_LONGITUD = "La URL es demasiado larga, la longitud máxima es de 1024 dígitos.";
    public final static String URL_NO_PATTERN = "La url obtenida no cumple con el patron de validación";
    public final static String OBJETO_ORDERS_INESPERADO = "El objeto que se espera de recibir tiene algún atributo inesperado";
    public final static String URL_ORDERS_MALA_STRUCTURA = "La url, que proporciona 'orders' paginados tiene estructura incompatible, debe tener elementos '/orders' obligatoriamente.";
    public final static String OBJETO_ORDER_INESPERADO = "Se ha producido un error al transformar la respuesta, seguramente la estructura del objeto recibido no corresponde con el esperado.";
    public final static String INSERT_MASIVO_FAIL = "Se ha producido un error a la hora de guardar los datos obtenidos en la base de datos";
    public final static String NO_SE_HA_FORMADO_PATH = "No se ha podido formar path del archivo final debido a : ";
    public final static String NO_SE_HA_INSERTADO_CSV_MASIVO = "No se ha podido insertar los registros por medio de un archivo CSV la razón: ";
    public final static String ERROR_GENERADOR_RESUMEN = "Se ha producido un error a la hora de elaborar el resumen de la descarga, por motivo: ";
    public final static String ERROR_GENERADOR_ARCHIVO_MASIVA = "Se ha producido un error a la hora de elaborar el archivo de carga masiva, motivo: ";
    public final static String ERROR_CERRAR_FILE_BUILDER = "Gestor documental se ha cerrado incorrectamente por motivo: ";
    public final static String ERROR_ELABORACION_ARCHIVO_REQUIRIDO = "Se ha producido un error a la hora de elaborar el archivo  ordenado, motivo: ";
    public final static String PAGE = "page";
    public final static String MAX_PER_PAGE = "max-per-page";
    public final static String VALIDACION_URL_ORDERS = "/orders";

    //generador documental
    public final static String ARCHIVO_URL = "C:/LIBROSAPI/";
    public final static String NOMBRE_ARCHIVO = "archivo_final";
    public final static String NOMBRE_ARCHIVO_INSERT = "archivo_final_insert";
    public final static String NOMBRE_ARCHIVO_RESUMEN_FINAL = "resumen_final";
    public final static String EXTENSION_ARCHIVO = ".csv";

    public final static String SALTO_LINEA = "\n";
    public final static String TABULACION = "\t";
    public final static String CABECERA_CSV = "Order ID, Order Priority, Order Date, Region, Country, Item Type, Sales Channel, Ship Date, Units Sold, Unit Price, Unit Cost, Total Revenue, Total Cost, Total Profit";
    public final static String CABECERA_CSV_INSERT = "id, country, date, error_conver, estado_registro, item_type, order_id, order_uuid, priority, region, sales_channel, ship_date, total_costs, total_profits, total_revenue, unit_cost, unit_price, units_sold, id_tarea;";
    public final static String PATRON_LIBRO_CON_SALTO_DE_LINEA = "%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,\n";
    public final static String PATRON_LIBRO_CON_SALTO_DE_LINEA_INSERT = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s;\n";
    public final static String SEPARADOR_LINEA = "---------------------------------------------------------------------------------------------";
    public final static String CABECERA_RESUMEN_FINAL = "RESUMEN FINAL Y CONTEO DE LOS CAMPOS IMPORTANTES: ";

    // funcionamiento guaradr API 
    public final static String PARTICION_VARIOS_CSV = "PARTICION";
    public final static String UNICO_CSV = "UNICO CSV";
    public final static String UNO_POR_UNO = "UNO";
    public final static String DE_MILL_EN_MILL = "MILL";

    public final static String REGION = "Region:";
    public final static String COUNTRY = "Country:";
    public final static String ITEM_TYPE = "Item Type:";
    public final static String SALES_CHANNEL = "Sales Channel:";
    public final static String ORDER_PRIORITY = "Order Priority:";
}
