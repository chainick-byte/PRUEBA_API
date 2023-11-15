/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 *
 * @author igorr
 */
public class OrdersDto {
    @JsonProperty("page")
    private String page;
    
    @JsonProperty("content")
    private List<Order> content;
    
    @JsonProperty("links")
    private Links links;
    
    private ErroresResponse erroresResponse;
    private String result;
    
    private Slice<Order> orderPagindo;
    private Pageable pageable;
    private Order orderSeleccionado;
    public OrdersDto() {
    }

    public Order getOrderSeleccionado() {
        return orderSeleccionado;
    }

    public void setOrderSeleccionado(Order orderSeleccionado) {
        this.orderSeleccionado = orderSeleccionado;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Order> getContent() {
        return content;
    }

    public void setContent(List<Order> content) {
        this.content = content;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
    
    public ErroresResponse getErroresResponse() {
        return erroresResponse;
    }

    public void setErroresResponse(ErroresResponse erroresResponse) {
        this.erroresResponse = erroresResponse;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Slice<Order> getOrderPagindo() {
        return orderPagindo;
    }

    public void setOrderPagindo(Slice<Order> orderPagindo) {
        this.orderPagindo = orderPagindo;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
    
    
    
    
    @Override
    public String toString() {
        return "OrdersDto{" + "page=" + page + ", content=" + content + ", links=" + links + ", erroresResponse=" + erroresResponse + '}';
    }

    
}
