/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.service;

import com.pruebaTech.demo.DTO.Order;
import com.pruebaTech.demo.Repository.LibrosRepository;
import com.pruebaTech.demo.controller.TareaController;
import com.pruebaTech.demo.modelos.Libro;
import com.pruebaTech.demo.modelos.Tarea;
import com.pruebaTech.demo.utils.Constantes;
import com.pruebaTech.demo.utils.GeneradorUUID;
import com.pruebaTech.demo.utils.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author igorr
 */
@Service
public class LibroService {

    private final Logger logger = LoggerFactory.getLogger(TareaController.class);

    @Autowired
    private LibrosRepository librosRepositorio;
    
    @Autowired
    private DataSource dataSource;
    
    private Utils util = new Utils();

    public Libro guardar(Order order, Tarea tarea) {
        try {
            return librosRepositorio.save(convertirOrersDtoToLibro(order, tarea));
        } catch (Exception e) {
            logger.error("===========> el error sucedio al guardar libro: " + order.toString());
        }
        return null;
    }

    @Transactional
    public void guagdarAll(List<Libro> libros) {
        try {
            librosRepositorio.saveAll(libros);
        } catch (Exception e) {
            logger.error("===========> el error sucedio al guagdarAll libro: " + e.toString());
        }
    }

    public Libro convertirOrersDtoToLibro(Order order, Tarea tarea) {
        Libro libro = new Libro();
        try {
            Utils util = new Utils();
            //en caso de insertar a traves del script añado id's
            libro.setId(new GeneradorUUID().uuid());
            libro.setIdTarea(tarea);
            libro.setOrderUuid(order.getUuid());
            libro.setOrderId(util.convertirStringToLong(libro, order.getOrderId()));
            libro.setRegion(order.getRegion());
            libro.setCountry(order.getCountry());
            libro.setItemType(order.getItem_type());
            libro.setSalesChannel(order.getSales_channel());
            libro.setPriority(order.getPriority());
            libro.setDate( order.getDate());
            libro.setShipDate(order.getShip_date());
            libro.setUnitsSold(util.convertirStringToLong(libro, order.getUnits_sold()));
            libro.setUnitPrice(util.convertirStringToBigDecimal(libro, order.getUnit_price()));
            libro.setUnitCost(util.convertirStringToBigDecimal(libro, order.getUnit_cost()));
            libro.setTotalRevenue(util.convertirStringToBigDecimal(libro, order.getTotal_revenue()));
            libro.setTotalCosts(util.convertirStringToBigDecimal(libro, order.getTotal_cost()));
            libro.setTotalProfits(util.convertirStringToBigDecimal(libro, order.getTotal_profit()));
        } catch (Exception e) {

            this.util.addErrorNuevo(libro, Constantes.ERROR_001004);
            logger.error("===========> el error sucedio al convertirOrersDtoToLibro: " + e.getMessage());
        }

        return libro;
    }

    public void cargarDatosDesdeArchivo(String generarPath) {
        
        logger.info("===========> comienzo del  cargarDatosDesdeUnArchivo generarPath: " + generarPath);
        
        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query = "LOAD DATA LOCAL INFILE '" + generarPath + "' INTO TABLE LIBROS.LIBROS " +
                           "FIELDS TERMINATED BY ',' LINES TERMINATED BY ';'";
            
            logger.info("===========> comienzo del  cargarDatosDesdeUnArchivo generarPath: " + query);
            statement.execute(query);
        } catch (SQLException e) {
           logger.error("===========> el error sucedio al cargarDatosDesdeUnArchivo: " + e.getMessage(), e);
        }
    }
}
