/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.DTO;

import com.pruebaTech.demo.utils.Constantes;

/**
 *
 * @author igorr
 */
public class ErroresResponse {
    /**
     */
    private String statusSimple;
    private String status;
    private String codigErrorPersonalizado = Constantes.ERROR_000000;
    private String textoError;

    public ErroresResponse() {
    }

    public ErroresResponse(String statusSimple, String status, String codigErrorPersonalizado, String textoError) {
        this.statusSimple = statusSimple;
        this.status = status;
        this.codigErrorPersonalizado = codigErrorPersonalizado;
        this.textoError = textoError;
    }

    public String getStatusSimple() {
        return statusSimple;
    }

    public void setStatusSimple(String statusSimple) {
        this.statusSimple = statusSimple;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodigErrorPersonalizado() {
        return codigErrorPersonalizado;
    }

    public void setCodigErrorPersonalizado(String codigErrorPersonalizado) {
        this.codigErrorPersonalizado = codigErrorPersonalizado;
    }

    public String getTextoError() {
        return textoError;
    }

    public void setTextoError(String textoError) {
        this.textoError = textoError;
    }

    @Override
    public String toString() {
        return "ErroresResponse{" + "statusSimple=" + statusSimple + ", status=" + status + ", codigErrorPersonalizado=" + codigErrorPersonalizado + ", textoError=" + textoError + '}';
    }
    
}
