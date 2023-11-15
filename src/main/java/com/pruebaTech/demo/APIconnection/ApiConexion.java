/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.APIconnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebaTech.demo.DTO.ErroresResponse;
import com.pruebaTech.demo.DTO.FormularioDTO;
import com.pruebaTech.demo.DTO.Links;
import com.pruebaTech.demo.DTO.Order;
import com.pruebaTech.demo.DTO.OrdersDto;
import com.pruebaTech.demo.IAPIconnection.IApiconexion;
import com.pruebaTech.demo.utils.Constantes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author igorr
 */
@Service
public class ApiConexion implements IApiconexion {

    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(ApiConexion.class);

    @Override
    public FormularioDTO getOrder(FormularioDTO formularioDto, String URL) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        ErroresResponse erroresResponse = new ErroresResponse();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            erroresResponse = obtenerStatusResponse(response, erroresResponse);
            HttpEntity entity = response.getEntity();

            if (entity != null && Constantes.HTTP_OK.equals(erroresResponse.getStatus())) {
                String responseBody = EntityUtils.toString(entity, "UTF-8");

                formularioDto = convertirResponseToDto(responseBody, formularioDto);
                // si la transformacion de JSON sucedio ok acepta el error de arriba
                if (Constantes.STATUS_OK.equals(formularioDto.getErroresResponse().getStatusSimple())) {
                    formularioDto.setErroresResponse(erroresResponse);
                    formularioDto.setResult(responseBody);
                }

                formularioDto.setURL(URL);

                return formularioDto;
            } else {
                formularioDto.setErroresResponse(erroresResponse);
            }
        } catch (Exception e) {
            logger.error("NO se ha producido conexion con la api: " + e.getMessage());
            formularioDto.setErroresResponse(new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.HTTP_SERVER_ERROR, Constantes.ERROR_100002, e.getMessage()));
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("NO se ha cerrado el httpClient " + e.getMessage());
            }
        }
        return formularioDto;
    }

    @Override
    public OrdersDto getAllOrders(OrdersDto ordersDto, String URL) {
        List<Order> allOrders = new ArrayList<>();
        int page = 1;
        boolean tieneMasPaginas = true;
        logger.error("===>allOrders URL : " + URL);
        while (tieneMasPaginas) {
            String pageUrl = urlBuilder(URL, page, Constantes.MILL);
            logger.error("===>allOrders URL : " + pageUrl);
            OrdersDto response = conexionOrders(pageUrl, ordersDto);
            ordersDto.setErroresResponse(response.getErroresResponse());
            if (Constantes.STATUS_OK.equals(response.getErroresResponse().getStatusSimple())) {
                List<Order> orders = response.getContent();
                if (orders != null && !orders.isEmpty()) {
                    allOrders.addAll(orders);
                    logger.error("===>allOrders " + allOrders.size());
                    page++;
                    //hasMorePages = false;
                } else {
                    tieneMasPaginas = false;
                }
            } else {
                ordersDto.setErroresResponse(response.getErroresResponse());
                tieneMasPaginas = false;
            }
        }
        logger.error("===>allOrders Final:" + allOrders.size());
        logger.error("===>allOrders errores:" + ordersDto.getErroresResponse().toString());
        ordersDto.setContent(allOrders);

        return ordersDto;
    }

    private FormularioDTO convertirResponseToDto(String responseBody, FormularioDTO formularioDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErroresResponse erroresResponse = new ErroresResponse();
        try {
            formularioDto = objectMapper.readValue(responseBody, FormularioDTO.class);
            erroresResponse.setStatusSimple(Constantes.STATUS_OK);
            formularioDto.setErroresResponse(erroresResponse);
            return formularioDto;
        } catch (JsonMappingException e) {

            erroresResponse.setStatusSimple(Constantes.STATUS_KOK);
            erroresResponse.setStatus(Constantes.HTTP_BAD_REQUEST);
            erroresResponse.setTextoError(Constantes.OBJETO_INESPERADO);
            erroresResponse.setCodigErrorPersonalizado(Constantes.ERROR_100001);
            formularioDto.setErroresResponse(erroresResponse);
            logger.error("NO se ha transformado JSON a objeto formualrioDto" + e.getMessage());

        }
        return formularioDto;
    }

    private ErroresResponse obtenerStatusResponse(CloseableHttpResponse response, ErroresResponse erroresResponse) {
        int statusRequest = response.getStatusLine().getStatusCode();
        String statusTexto = response.getStatusLine().getReasonPhrase();
        // sit todo fue ok o has recogido toda la api 
        if (statusRequest == Constantes.INTEGER_HTTP_OK || statusRequest == Constantes.INTEGER_HTTP_CONTENT_NOF_FOUND) {
            erroresResponse.setStatusSimple(Constantes.STATUS_OK);
        } else {
            erroresResponse.setStatusSimple(Constantes.STATUS_KOK);
            erroresResponse.setTextoError(statusTexto);
        }
        erroresResponse.setStatus(String.valueOf(statusRequest));
        return erroresResponse;
    }

    public OrdersDto conexionOrders(String URL, OrdersDto ordersDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        ErroresResponse erroresResponse = new ErroresResponse();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            erroresResponse = obtenerStatusResponse(response, erroresResponse);
            HttpEntity entity = response.getEntity();
            if (entity != null && (Constantes.HTTP_OK.equals(erroresResponse.getStatus())
                    || Constantes.HTTP_CONTENT_NOF_FOUND.equals(erroresResponse.getStatus()))) {
                String responseBody = EntityUtils.toString(entity, "UTF-8");
                ordersDto = convertirResponseToOrdersDto(responseBody, ordersDto);
                if (Constantes.STATUS_OK.equals(ordersDto.getErroresResponse().getStatusSimple())) {
                    ordersDto.setErroresResponse(erroresResponse);
                    ordersDto.setResult(responseBody);
                }
                return ordersDto;
            } else {
                ordersDto.setErroresResponse(erroresResponse);
            }
        } catch (Exception e) {
            logger.error("NO se ha producido conexion con la api: " + e.getMessage());
            ordersDto.setErroresResponse(new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.HTTP_SERVER_ERROR, Constantes.ERROR_100002, e.getMessage()));
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("NO se ha cerrado el httpClient " + e.getMessage());
            }
        }
        return ordersDto;
    }

    private OrdersDto convertirResponseToOrdersDto(String responseBody, OrdersDto ordersDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        ErroresResponse erroresResponse = null;
        try {
            ordersDto = objectMapper.readValue(responseBody, OrdersDto.class);
            erroresResponse = new ErroresResponse(Constantes.STATUS_OK, Constantes.HTTP_OK, Constantes.ERROR_000000, null);
        } catch (Exception e) {
            erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.HTTP_BAD_REQUEST, Constantes.ERROR_010001, Constantes.OBJETO_ORDERS_INESPERADO);
            logger.error("NO se ha convertido objeto order a ordersDto " + e.getMessage());
        }
        ordersDto.setErroresResponse(erroresResponse);
        return ordersDto;
    }

    @Override
    public OrdersDto getOrdersPaginados(OrdersDto ordersDto, String apiUrl) {
        try {
            boolean hasNext = true;
            Pageable pageable = ordersDto.getPageable();
            logger.info("obtenerOrders<---- pageable formado: page = " + pageable.getPageNumber() + " size = " + pageable.getPageSize());
            // Configura los parámetros de la solicitud HTTP para la paginación
            String urlFormada = urlBuilder(apiUrl, pageable.getPageNumber(), pageable.getPageSize());
            ResponseEntity<OrdersDto> responseEntity = restTemplate.exchange(
                    urlFormada,
                    HttpMethod.GET,
                    null,
                    OrdersDto.class
            );
            OrdersDto apiResponse = responseEntity.getBody();
            // Suponiendo que la respuesta contiene datos paginados
            Links link = apiResponse.getLinks();
            logger.info("link formado: " + apiResponse.getContent().size());
            if (link != null) {
                if (link.getNext() != null) {
                    hasNext = true;
                } else {
                    hasNext = false;
                }
            }
            List<Order> productos = apiResponse.getContent();
            ordersDto = convertirListaOrdersToString(apiResponse, apiResponse);
            ordersDto.setContent(apiResponse.getContent());
            ordersDto.setOrderPagindo(new SliceImpl<>(productos, pageable, hasNext));
        } catch (Exception e) {
            ordersDto.setErroresResponse(new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.ERROR, Constantes.ERROR_010003, e.getMessage()));
            return ordersDto;
        }

        return ordersDto;
    }

    private OrdersDto convertirListaOrdersToString(OrdersDto ordersDto, OrdersDto apiResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        ErroresResponse erroresResponse = null;
        try {
            ordersDto.setResult(objectMapper.writeValueAsString(ordersDto.getContent()));
            //erroresResponse = new ErroresResponse(Constantes.STATUS_OK, null, Constantes.ERROR_000000, null);
        } catch (Exception e) {
            erroresResponse = new ErroresResponse(Constantes.STATUS_KOK,
                    Constantes.HTTP_BAD_REQUEST, Constantes.ERROR_010004, Constantes.OBJETO_ORDERS_INESPERADO);
            logger.error("NO se ha convertirListaOrdersToJson objeto order a ordersDto " + e.getMessage());
        }
        ordersDto.setErroresResponse(erroresResponse);
        return ordersDto;
    }

    private String urlBuilder(String apiUrl, int pageNumber, int pageSize) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam(Constantes.PAGE, pageNumber)
                .queryParam(Constantes.MAX_PER_PAGE, pageSize);
        return uriBuilder.toUriString();
    }

    public OrdersDto getOrderDetailsByUUID(OrdersDto orderDetails, String url) {
        try {
            ResponseEntity<Order> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    Order.class
            );
            orderDetails.setOrderSeleccionado(responseEntity.getBody());
        } catch (Exception e) {
            orderDetails = new OrdersDto();
            orderDetails.setErroresResponse(new ErroresResponse(
                    Constantes.STATUS_KOK,
                    Constantes.ERROR,
                    Constantes.ERROR_010003,
                    e.getMessage()
            ));
        }
        return orderDetails;
    }
}
