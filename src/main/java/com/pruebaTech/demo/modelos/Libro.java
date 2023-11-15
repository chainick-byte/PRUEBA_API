/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author igorr
 */
@Entity
@Table(name = "LIBROS")
public class Libro implements Serializable{
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy ="org.hibernate.id.UUIDGenerator")
    private String id;
    
    @Column(name = "ORDER_UUID")
    private String orderUuid;
    
    @Column( name = "ORDER_ID")
    private Long orderId;
    
    @Column( name = "REGION")
    private String region;
    
    @Column( name = "COUNTRY")
    private String country;
    
    @Column( name = "ITEM_TYPE")
    private String itemType;
    
    @Column( name = "SALES_CHANNEL")
    private String salesChannel;
    
    @Column( name = "PRIORITY")
    private String priority;
    
    @Column( name = "DATE")
    private Date date;
    
    @Column( name = "SHIP_DATE")
    private Date shipDate;
    
    @Column( name = "UNITS_SOLD")
    private Long unitsSold;
    
    @Column( name = "UNIT_PRICE", precision = 16, scale = 2)
    private BigDecimal unitPrice;
    
    @Column( name = "UNIT_COST", precision = 16, scale = 2)
    private BigDecimal unitCost;
    
    @Column( name = "TOTAL_REVENUE", precision = 16, scale = 2)
    private BigDecimal totalRevenue;
    
    @Column( name = "TOTAL_COSTS", precision = 16, scale = 2)
    private BigDecimal totalCosts;
    
    @Column( name = "TOTAL_PROFITS", precision = 16, scale = 2)
    private BigDecimal totalProfits;
    
    @ManyToOne
    @JoinColumn(name="ID_TAREA", nullable=false)
    private Tarea idTarea;
    
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro = 1;
    
    @Column(name ="ERROR_CONVER")
    private String errorCOnversion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Long getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(Long unitsSold) {
        this.unitsSold = unitsSold;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(BigDecimal totalCosts) {
        this.totalCosts = totalCosts;
    }

    public BigDecimal getTotalProfits() {
        return totalProfits;
    }

    public void setTotalProfits(BigDecimal totalProfits) {
        this.totalProfits = totalProfits;
    }
    
    
    
    public Tarea getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Tarea idTarea) {
        this.idTarea = idTarea;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getErrorCOnversion() {
        return errorCOnversion;
    }

    public void setErrorCOnversion(String errorCOnversion) {
        this.errorCOnversion = errorCOnversion;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", orderUuid=" + orderUuid + ", orderId=" + orderId + ", region=" + region + ", country=" + country + ", itemType=" + itemType + ", salesChannel=" + salesChannel + ", priority=" + priority + ", date=" + date + ", shipDate=" + shipDate + ", unitsSold=" + unitsSold + ", unitPrice=" + unitPrice + ", unitCost=" + unitCost + ", totalRevenue=" + totalRevenue + ", totalCosts=" + totalCosts + ", totalProfits=" + totalProfits + ", idTarea=" + idTarea + ", estadoRegistro=" + estadoRegistro + ", errorCOnversion=" + errorCOnversion + '}';
    }
 
}
