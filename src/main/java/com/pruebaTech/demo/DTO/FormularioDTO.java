/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author igorr
 */
public class FormularioDTO {
    
    private String idTArea;
    private String URL;
    private String result;

    @JsonProperty("bookmarks")
    private String bookmarks;
    
    @JsonProperty("orders")
    private String orders;
    
    private List<TareaDto> listaTareaActivas;
    
    private ErroresResponse erroresResponse;

    public FormularioDTO() {
    }
    
    
    public String getIdTArea() {
        return idTArea;
    }

    public FormularioDTO(String idTArea, String URL) {
        this.idTArea = idTArea;
        this.URL = URL;
    }
    
    public void setIdTArea(String idTArea) {
        this.idTArea = idTArea;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(String bookmarks) {
        this.bookmarks = bookmarks;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ErroresResponse getErroresResponse() {
        return erroresResponse;
    }

    public void setErroresResponse(ErroresResponse erroresResponse) {
        this.erroresResponse = erroresResponse;
    }

    public List<TareaDto> getListaTareaActivas() {
        return listaTareaActivas;
    }

    public void setListaTareaActivas(List<TareaDto> listaTareaActivas) {
        this.listaTareaActivas = listaTareaActivas;
    }
    
    @Override
    public String toString() {
        return "FormularioDTO{" + "idTArea=" + idTArea + ", URL=" + URL + ", result=" + result + ", bookmarks=" + bookmarks + ", orders=" + orders + ", erroresResponse=" + erroresResponse + '}';
    }

}
