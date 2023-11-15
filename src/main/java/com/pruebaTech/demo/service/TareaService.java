/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.service;

import com.pruebaTech.demo.DTO.TareaDto;
import com.pruebaTech.demo.Repository.TareaRepository;
import com.pruebaTech.demo.controller.TareaController;
import com.pruebaTech.demo.modelos.Tarea;
import com.pruebaTech.demo.utils.Constantes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author igorr
 */
@Service
public class TareaService {
    
    private final Logger logger = LoggerFactory.getLogger(TareaController.class);
    
    @Autowired
    private TareaRepository tareaRepository;
    
    public Tarea guardar(Tarea tarea){
        try {  
            return tareaRepository.save(tarea);
        } catch (Exception e) {
            logger.error("guardando el objet de tarea: " + e.getMessage());
        }
        return tarea;
    }
    
    public void actualizaMiTarea(String idTarea, Long tiempo, String Url, String funcionamiento, String error){
        Tarea miTarea = tareaRepository.findById(idTarea).orElse(null);
        if (miTarea != null){
            miTarea.setTiempo(tiempo);
            miTarea.setUrl(Url);
            miTarea.setFuncionamiento(funcionamiento);
            miTarea.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
            miTarea.setHayError(error);
            tareaRepository.save(miTarea);
            logger.info("mi tarea se ha actualizado correctamente: FIN" );
        }
    }
    
    public List<TareaDto> getTodasLAsTAreasActivas(){
        List<TareaDto> tareasADevolver = null; 
        List<Tarea> misTareasActivas = tareaRepository.findByEstadoRegistroOrderByFechaCreacionDesc(Constantes.ESTADO_ACTIVO);
        
        if (misTareasActivas != null && !misTareasActivas.isEmpty()){
            tareasADevolver = new ArrayList<>();
            for (Tarea t : misTareasActivas) {
                TareaDto tarea = new TareaDto();
                tarea.setTiempo((t.getTiempo()!=null&&t.getTiempo()!=0) ? t.getTiempo()/1000 : 0);
                tarea.setUrl(t.getUrl());
                tarea.setModo(t.getFuncionamiento());
                tarea.setId(t.getIdTarea());
                tarea.setError(t.getHayError());
                tareasADevolver.add(tarea);
            }
        }
        return tareasADevolver != null ? tareasADevolver : null;
    }

}
