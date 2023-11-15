/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.DTO;

/**
 *
 * @author igorr
 */
public class TareaDto {
    
    private String id;
    private String url;
    private Long tiempo;
    private String modo;
    private String Error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    @Override
    public String toString() {
        return "TareaDto{" + "id=" + id + ", url=" + url + ", tiempo=" + tiempo + ", modo=" + modo + ", Error=" + Error + '}';
    }
    
}
