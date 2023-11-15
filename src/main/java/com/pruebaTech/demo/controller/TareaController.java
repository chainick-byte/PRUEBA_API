/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.controller;

import com.pruebaTech.demo.APIconnection.ApiConexion;
import com.pruebaTech.demo.DTO.ErroresResponse;
import com.pruebaTech.demo.DTO.FormularioDTO;
import com.pruebaTech.demo.DTO.Order;
import com.pruebaTech.demo.DTO.OrdersDto;
import com.pruebaTech.demo.DTO.TareaDto;
import com.pruebaTech.demo.generadorDocumental.GeneradorDocumental;
import com.pruebaTech.demo.modelos.Libro;
import com.pruebaTech.demo.modelos.Tarea;
import com.pruebaTech.demo.service.LibroService;
import com.pruebaTech.demo.service.ResumenFinalService;
import com.pruebaTech.demo.service.TareaService;
import com.pruebaTech.demo.utils.Constantes;
import com.pruebaTech.demo.validaciones.ValidacionService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author igorr
 */
@Controller
public class TareaController {

    private boolean para = false;
    private FormularioDTO formulario;
    private static OrdersDto order;
    private String url = "";
    private final Logger logger = LoggerFactory.getLogger(TareaController.class);

    @Autowired
    private ApiConexion apiConexion;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ValidacionService validacionService;

    @Autowired
    private LibroService libroSerice;

    @Autowired
    private GeneradorDocumental generadorDocumental;
    
    @Autowired
    private ResumenFinalService resumenFinalService;

    @GetMapping("/")
    public String tareaNueva(Model model) {
        FormularioDTO formularioDto = new FormularioDTO();
        List<TareaDto> listaTareas = tareaService.getTodasLAsTAreasActivas();
        formularioDto.setListaTareaActivas(listaTareas);
        model.addAttribute("formularioDto", formularioDto);
        return Constantes.INDEX;
    }

    @GetMapping("/orders")
    public String verOrders(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {

        OrdersDto ordersDto = new OrdersDto();

        logger.info("}}}}======>>>>>url order recibido " + this.formulario.toString());
        String urlOrders = this.formulario.getOrders();
        logger.info("}}}}======>>>>>url order recibido " + urlOrders);
        ErroresResponse erroresResponse = validacionService.validarUrlEntrada(urlOrders, Constantes.VER_ORDERS);
        logger.info("Comprobacion de url ordders "
                + (erroresResponse != null ? erroresResponse.toString() : "no hay errres en la url de orders"));
        if (erroresResponse != null && !Constantes.STATUS_OK.equals(erroresResponse.getStatusSimple())) {
            ordersDto.setErroresResponse(erroresResponse);
            return Constantes.VER_ORDERS;
        } else {

            int page = pageable.getPageNumber();
            int size = pageable.getPageSize();
            pageable = PageRequest.of(page, size);
            ordersDto.setPageable(pageable);
            logger.info("}}}}======>>>>>page || size -- " + page + " ||" + size);
            ordersDto = apiConexion.getOrdersPaginados(ordersDto, urlOrders);
            logger.info("}}}}======>>>>>orders paginado recibido  " + (ordersDto.getErroresResponse() != null
                    ? ordersDto.getErroresResponse().toString() : "no se ha producido errores"));
            logger.info("}}}}======>>>>>ordersDto----> content : " + (ordersDto.getContent() != null
                    ? ordersDto.getContent().size() : "no hay contenido"));
        }
        this.order = ordersDto;
        model.addAttribute("ordersDto", ordersDto);
        return Constantes.VER_ORDERS;
    }

    @GetMapping("/orders/{uuid}")
    public String verDetallesDelPedido(@PathVariable String uuid, Model model) {
        logger.info("}}}}======>>>>>verDetallesDelPedido <====>{comienzo} uuid :" + uuid);
        OrdersDto orderDetails = new OrdersDto();
        Order seleccionado = this.order.getContent().stream()
                .filter(order -> order.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
        //logger.info("}}}}======>>>>>verDetallesDelPedido <====>{seleccionado} uuid :" + seleccionado.toString());
        if (seleccionado != null) {
            orderDetails = apiConexion.getOrderDetailsByUUID(orderDetails, seleccionado.getLinks().getSelf());
        } else {
            model.addAttribute("ordersDto", this.order);
            return Constantes.VER_ORDERS;
        }

        logger.info("}}}}======>>>>>verDetallesDelPedido <====>{despues de api } orderSeleccionado :" + orderDetails.getOrderSeleccionado().toString());

        model.addAttribute("orderDetails", orderDetails);

        return Constantes.VER_DETALLE;
    }

    @PostMapping("/ejecutar-metodo")
    public String ejecutarMetodo(@RequestBody FormularioDTO formularioDto, Model model) {
        url = formularioDto.getURL();
        String result = Constantes.EMPTY;
        ErroresResponse erroresResponse = validacionService.validarUrlEntrada(url, Constantes.EMPTY);
        if (erroresResponse != null && Constantes.STATUS_KOK.equals(erroresResponse.getStatusSimple())) {
            formularioDto.setErroresResponse(erroresResponse);
        } else {
            formularioDto = apiConexion.getOrder(formularioDto, url);
            logger.info("FORMULARIODTO= " + formularioDto.toString());
            if (Constantes.STATUS_OK.equals(formularioDto.getErroresResponse().getStatusSimple())) {

                //this.fragmentoVisible = false;
                this.formulario = formularioDto;
                result = Constantes.FRAGMENTO;
            } else {
                //this.fragmentoVisible = true;
            }
        }
        model.addAttribute("formularioDto", formularioDto);
        return result;
    }

    @PostMapping("/orders/pantallaGuardar/insertarDeMillEnMIll")
    public String guardarDatosInsertarDeMillEnMIll(Model model) {
        ErroresResponse er = null;
        long inicio = System.currentTimeMillis();
        String error = "";
        this.para = true;
        final Tarea tarea = tareaService.guardar(new Tarea());
        try {

            OrdersDto todosOrdersAPI = new OrdersDto();
            String urlOrders = this.formulario.getOrders();
            todosOrdersAPI = apiConexion.getAllOrders(todosOrdersAPI, urlOrders);

            logger.info("}}}}======>>>>>{guardarDatosInsertarDeMillEnMIll}----> llamada a la api errores :"
                    + todosOrdersAPI.getErroresResponse() != null
                    ? todosOrdersAPI.getErroresResponse().toString() : "no llegan errores");

            if (todosOrdersAPI.getErroresResponse() != null
                    && Constantes.STATUS_OK.equals(todosOrdersAPI.getErroresResponse().getStatusSimple())) {

                List<Order> orders = todosOrdersAPI.getContent();
                List<Libro> librosOrdenados = ordenarListaDeLibros(orders, tarea);
                guardadoMasivo(librosOrdenados);
                do {
                    er = generadorDocumental.generadorCSV(tarea.getIdTarea(), librosOrdenados);
                    er = generadorDocumental.generadorResumen(tarea, librosOrdenados);
                    this.para = false;
                } while (er == null && this.para);

                if (er != null) {
                    error = er.getCodigErrorPersonalizado();
                    logger.error("}}}}======>>>>>{guardarDatosInsertarDeMillEnMIll}----> errores docuemtales :"
                            + er.toString());
                }
            } else {
                error = todosOrdersAPI.getErroresResponse().getCodigErrorPersonalizado();
                logger.error("}}}}======>>>>>{guardarDatosInsertarDeMillEnMIll}----> errores de la api :"
                        + todosOrdersAPI.getErroresResponse().toString());
            }
        } catch (Exception e) {
            er = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                    Constantes.ERROR_010005, Constantes.INSERT_MASIVO_FAIL);
            error = Constantes.ERROR_010005;
            logger.info("}}}}======>>>>>{guardarDatosInsertarDeMillEnMIll}----> guardar errores :" + er.toString());
            logger.info("}}}}======>>>>>{guardarDatosInsertarDeMillEnMIll}----> guardar lista de orders : " + e.getMessage());
        }
        long fin = System.currentTimeMillis();
        tareaService.actualizaMiTarea(tarea.getIdTarea(), (fin - inicio), url, Constantes.DE_MILL_EN_MILL, error);
        model.addAttribute("ordersDto", this.order);
        return Constantes.REDIRECT_TO_INDEX;
    }

    @PostMapping("/orders/pantallaGuardar/insertarConUnUnicoArchivoCsv")
    public String guardarDatosInsertarConUnUnicoArchivoCsv(Model model) {
        ErroresResponse er = null;
        long inicio = System.currentTimeMillis();
        String error = "";
        this.para = true;
        final Tarea tarea = tareaService.guardar(new Tarea());

        try {

            OrdersDto todosOrdersAPI = new OrdersDto();
            String urlOrders = this.formulario.getOrders();
            todosOrdersAPI = apiConexion.getAllOrders(todosOrdersAPI, urlOrders);
            // errores de la api
            if (todosOrdersAPI.getErroresResponse() != null
                    && Constantes.STATUS_OK.equals(todosOrdersAPI.getErroresResponse().getStatusSimple())) {

                List<Order> orders = todosOrdersAPI.getContent();
                List<Libro> librosOrdenados = ordenarListaDeLibros(orders, tarea);
                do {
                    er = generadorDocumental.generadorCSVCargaMasiva(tarea.getIdTarea(), Constantes.SIN_PREFIJO, librosOrdenados);
                    libroSerice.cargarDatosDesdeArchivo(generadorDocumental.generarPath(tarea.getIdTarea(), Constantes.SIN_PREFIJO,
                            er, Constantes.NOMBRE_ARCHIVO_INSERT));
                    er = generadorDocumental.generadorCSV(tarea.getIdTarea(), librosOrdenados);
                    er = generadorDocumental.generadorResumen(tarea, librosOrdenados);
                    this.para = false;
                } while (er == null && para);
                if (er != null) {
                    error = er.getCodigErrorPersonalizado();
                    logger.error("}}}}======>>>>>{guardarDatosInsertarConUnaParticion}----> guardar errores :" + er.toString());
                }
            } else {
                er = todosOrdersAPI.getErroresResponse();
                error = todosOrdersAPI.getErroresResponse().getCodigErrorPersonalizado();
                logger.info("}}}}======>>>>>{guardarDatosInsertarConUnUnicoArchivoCsv}----> guardar errores :" + todosOrdersAPI.getErroresResponse().toString());
            }

        } catch (Exception e) {
            er = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                    Constantes.ERROR_010005, Constantes.INSERT_MASIVO_FAIL);
            error = Constantes.ERROR_010005;
            logger.info("}}}}======>>>>>{guardarDatosInsertarConUnUnicoArchivoCsv}----> guardar errores :" + er.toString());
            logger.info("}}}}======>>>>>{guardarDatosInsertarConUnUnicoArchivoCsv}----> guardar lista de orders : " + e.getMessage());
        }

        long fin = System.currentTimeMillis();

        tareaService.actualizaMiTarea(tarea.getIdTarea(), (fin - inicio), url, Constantes.UNICO_CSV, error);
        model.addAttribute("ordersDto", this.order);
        return Constantes.REDIRECT_TO_INDEX;

    }

    @PostMapping("/orders/pantallaGuardar/unoPorUno")
    public String guardarDatosUnoPorUno(Model model) {
        this.para = true;
        ErroresResponse er = null;
        String error = "";
        long inicio = System.currentTimeMillis();

        final Tarea tarea = tareaService.guardar(new Tarea());

        try {

            OrdersDto todosOrdersAPI = new OrdersDto();
            String urlOrders = this.formulario.getOrders();
            todosOrdersAPI = apiConexion.getAllOrders(todosOrdersAPI, urlOrders);
            if (todosOrdersAPI.getErroresResponse() != null
                    && Constantes.STATUS_OK.equals(todosOrdersAPI.getErroresResponse().getStatusSimple())) {

                List<Order> orders = todosOrdersAPI.getContent();
                List<Libro> librosOrdenados = ordenarListaDeLibros(orders, tarea);
                logger.info("}}}}======>>>>>{guardarDatosUnoPorUno}----> comienza a insertar:");
                for (Order o : orders) {
                    libroSerice.guardar(o, tarea);
                }
                logger.info("}}}}======>>>>>{guardarDatosUnoPorUno}----> finalizo de insertar:");
                do {
                    er = generadorDocumental.generadorCSV(tarea.getIdTarea(), librosOrdenados);
                    er = generadorDocumental.generadorResumen(tarea, librosOrdenados);
                    this.para = false;
                } while (er == null && para);

                if (er != null) {
                    error = er.getCodigErrorPersonalizado();
                    logger.info("}}}}======>>>>>{guardarDatosUnoPorUno}----> errores en la generacion del archivo :"
                            + er.getCodigErrorPersonalizado().toString());
                }
            } else {
                error = todosOrdersAPI.getErroresResponse().getCodigErrorPersonalizado();
                logger.info("}}}}======>>>>>{guardarDatosUnoPorUno}----> errores en la consumicion de la API :"
                        + todosOrdersAPI.getErroresResponse().toString());
            }

        } catch (Exception e) {
            error = Constantes.ERROR_010005;
            er = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                    Constantes.ERROR_010005, Constantes.INSERT_MASIVO_FAIL);
            logger.info("}}}}======>>>>>{guardarDatosUnoPorUno}----> guardar errores :" + er.toString());
            logger.info("}}}}======>>>>>{guardarDatosUnoPorUno}----> guardar lista de orders : " + e.getMessage());
        }
        long fin = System.currentTimeMillis();
        tareaService.actualizaMiTarea(tarea.getIdTarea(), (fin - inicio), url, Constantes.UNO_POR_UNO, error);
        model.addAttribute("ordersDto", this.order);
        return Constantes.REDIRECT_TO_INDEX;
    }

    @PostMapping("/orders/pantallaGuardar/insertarConUnaParticion")
    public String guardarDatosInsertarConUnaParticion(Model model) {
        ErroresResponse er = null;
        String error = "";
        this.para = true;
        long inicio = System.currentTimeMillis();

        final Tarea tarea = tareaService.guardar(new Tarea());

        try {

            OrdersDto todosOrdersAPI = new OrdersDto();
            String urlOrders = this.formulario.getOrders();
            todosOrdersAPI = apiConexion.getAllOrders(todosOrdersAPI, urlOrders);
            if (todosOrdersAPI.getErroresResponse() != null
                    && Constantes.STATUS_OK.equals(todosOrdersAPI.getErroresResponse().getStatusSimple())) {

                List<Order> orders = todosOrdersAPI.getContent();
                List<Libro> librosOrdenados = ordenarListaDeLibros(orders, tarea);
                int tamanoSublista = Constantes.TAMANNO_PARTICION;

                do {
                    int tamañoLista = librosOrdenados.size();
                    int auxiliar = 0;
                    List<List<Libro>> listasSeparadas = new ArrayList<>();
                    for (int i = 0; i < tamañoLista; i += tamanoSublista) {

                        if (tamañoLista - i >= tamanoSublista) {
                            int fin = Math.min(tamañoLista, i + tamanoSublista);
                            listasSeparadas.add(librosOrdenados.subList(i, fin));
                        } else {
                            listasSeparadas.add(librosOrdenados.subList(i, tamañoLista));
                        }
                        er = generadorDocumental.generadorCSVCargaMasiva(tarea.getIdTarea(), auxiliar, listasSeparadas.get(auxiliar));
                        if (er == null) {
                            libroSerice.cargarDatosDesdeArchivo(generadorDocumental.generarPath(tarea.getIdTarea(),
                                    auxiliar, er, Constantes.NOMBRE_ARCHIVO_INSERT));
                        }
                        auxiliar++;
                    }

                    er = generadorDocumental.generadorCSV(tarea.getIdTarea(), librosOrdenados);
                    er = generadorDocumental.generadorResumen(tarea, librosOrdenados);
                    this.para = false;
                } while (er == null && this.para);
                if (er != null) {
                    error = er.getCodigErrorPersonalizado();
                    logger.error("}}}}======>>>>>{guardarDatosInsertarConUnaParticion}----> guardar errores :" + er.toString());
                }
            } else {
                error = todosOrdersAPI.getErroresResponse().getCodigErrorPersonalizado();
                logger.error("}}}}======>>>>>{guardarDatosInsertarConUnaParticion}----> guardar errores :"
                        + todosOrdersAPI.getErroresResponse().toString());
            }

        } catch (Exception e) {
            error = Constantes.ERROR_010005;
            er = new ErroresResponse(Constantes.STATUS_KOK, Constantes.ERROR,
                    Constantes.ERROR_010005, Constantes.INSERT_MASIVO_FAIL);
            logger.error("}}}}======>>>>>{guardarDatosInsertarConUnaParticion}----> guardar errores :" + er.toString());
            logger.error("}}}}======>>>>>{guardarDatosInsertarConUnaParticion}----> guardar lista de orders : " + e.getMessage());
        }
        long fin = System.currentTimeMillis();
        tareaService.actualizaMiTarea(tarea.getIdTarea(), (fin - inicio), url, Constantes.PARTICION_VARIOS_CSV, error);
        model.addAttribute("ordersDto", this.order);
        return Constantes.REDIRECT_TO_INDEX;
    }

    @GetMapping("/orders/pantallaGuardar")
    public String pantallaGuardar(Model model) {
        logger.info("}}}}======>>>>>{pantallaGuardar}----> comienzo : ");
        return Constantes.PANTALLA_GUARDAR;
    }

    private List<Libro> ordenarListaDeLibros(List<Order> orders, Tarea tarea) {
        logger.info("}}}}======>>>>>{ordenarListaDeLibros}----> inicio de ordenamiento<- ");
        List<Libro> result = orders.stream()
                .map(order -> libroSerice.convertirOrersDtoToLibro(order, tarea))
                .sorted(Comparator.comparing((Libro libro) -> libro.getOrderId()))
                .collect(Collectors.toList());
        orders = null;
        logger.info("}}}}======>>>>>{ordenarListaDeLibros}----> final de ordenamiento<- ");
        return result;
    }

    private void guardadoMasivo(List<Libro> librosOrdenados) {
        logger.info("}}}}======>>>>>comienza a insertar de mill en mill ");
        int lote = Constantes.MILL;
        for (int i = 0; i < librosOrdenados.size(); i += lote) {
            int finalIndex = Math.min(i + lote, librosOrdenados.size());
            List<Libro> loteAGuardar = librosOrdenados.subList(i, finalIndex);
            libroSerice.guagdarAll(loteAGuardar);
        }
        logger.info("}}}}======>>>>>finaliza a insertar de mill en mill ");
    }
    
    @GetMapping("/verDetalle/{id}")
    public String verDetalle(@PathVariable("id") String idTarea, Model model) {
        // Aquí se obtiene la tarea correspondiente al ID
        Map<String, Map<String, Long>> resumen = resumenFinalService.obtenerResumen(idTarea);

        // Añade la tarea al modelo para mostrar los detalles en la vista
        model.addAttribute("resumen", resumen);

        // Retorna el nombre de la vista que mostrará los detalles de la tarea
        return "detalleTarea"; // Suponiendo que tienes una vista llamada "detalleTarea.html"
    }
}
