package com.pruebaTech.demo.IAPIconnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.pruebaTech.demo.DTO.FormularioDTO;
import com.pruebaTech.demo.DTO.OrdersDto;

/**
 *
 * @author igorr
 */
public interface IApiconexion {
    
    //metodo que accede a la primera url de donde se saca la url de order
    FormularioDTO getOrder( FormularioDTO formularioDto, String URL);
    //metodo que obtiene la response de la api paginado para visualizarlo
    OrdersDto getOrdersPaginados (OrdersDto ordersDto, String URL);
    // metodo que obtiene todos los objetos orders de la api para guardar
    OrdersDto getAllOrders(OrdersDto ordersDto, String URL);
    // metodo que obtiene un solo order para ver detalle
    OrdersDto getOrderDetailsByUUID(OrdersDto orderDetails, String url);
    
    
}
