/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.utils;

import java.util.UUID;
import java.util.function.Supplier;

/**
 *
 * @author igorr
 */
public class GeneradorUUID {
    
    public String uuid (){
        Supplier<UUID> uuidSupplier = UUID::randomUUID;
        UUID uuid = uuidSupplier.get();
        return uuid.toString();
    }
    
}
