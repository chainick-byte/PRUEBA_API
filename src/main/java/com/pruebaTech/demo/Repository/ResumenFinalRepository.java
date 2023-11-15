/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.Repository;

import com.pruebaTech.demo.modelos.ResumenFinal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author igorr
 */
@Repository
public interface ResumenFinalRepository extends JpaRepository<ResumenFinal, String>{
    
    List<ResumenFinal> findByTareaIdTarea(String idTarea);
}
