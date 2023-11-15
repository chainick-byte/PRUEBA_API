/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.service;

import com.pruebaTech.demo.Repository.ResumenFinalRepository;
import com.pruebaTech.demo.controller.TareaController;
import com.pruebaTech.demo.modelos.ResumenFinal;
import com.pruebaTech.demo.modelos.Tarea;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author igorr
 */
@Service
public class ResumenFinalService {

    private final Logger logger = LoggerFactory.getLogger(ResumenFinalService.class);

    @Autowired
    private ResumenFinalRepository resumenFinalRepository;

    public void guardarMap(Map<String, Map<String, Long>> map, Tarea tarea) {
        logger.info("}}====> guardarMap comienza guardar todos los resumenes");
        for (Map.Entry<String, Map<String, Long>> entry : map.entrySet()) {
            String campoRelevante = entry.getKey();
            Map<String, Long> claveValorMap = entry.getValue();

            for (Map.Entry<String, Long> subEntry : claveValorMap.entrySet()) {
                String clave = subEntry.getKey();
                Long valor = subEntry.getValue();

                ResumenFinal resumen = new ResumenFinal();
                resumen.setCampoRelevante(campoRelevante);
                resumen.setClave(clave);
                resumen.setValor(String.valueOf(valor));
                resumen.setTarea(tarea);

                resumenFinalRepository.save(resumen);
            }
        }
        logger.info("}}====> guardarMap finaliza de  guardar todos los resumenes");
    }

    public Map<String, Map<String, Long>> obtenerResumen(String idTarea) {
        logger.info("}}====> obtenerResumen inicio obtencion de datos");
        Map<String, Map<String, Long>> resumen = new HashMap<>();
        List<ResumenFinal> listaResumen = resumenFinalRepository.findByTareaIdTarea(idTarea);
        for (ResumenFinal resumenFinal : listaResumen) {
            String campoRelevante = resumenFinal.getCampoRelevante();
            String clave = resumenFinal.getClave();
            Long valor = Long.parseLong(resumenFinal.getValor()); // Convertir a Long si es un String

            resumen.computeIfAbsent(campoRelevante, k -> new HashMap<>());
            resumen.get(campoRelevante).merge(clave, valor, Long::sum);
        }
        logger.info("}}====> obtenerResumen finalizo obtencion de datos");
        return resumen;
    }

}
