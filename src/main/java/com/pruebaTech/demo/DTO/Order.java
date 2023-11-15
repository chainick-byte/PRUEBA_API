/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 *
 * @author igorr
 */
public class Order {
    
@JsonProperty("uuid")    
private String uuid;

@JsonProperty("id")
private String orderId;

@JsonProperty("region")
private String region;

@JsonProperty("country")
private String country;

@JsonProperty("item_type")
private String item_type;

@JsonProperty("sales_channel")
private String sales_channel;

@JsonProperty("priority")
private String priority;

@JsonProperty("date")
@JsonFormat(pattern = "MM/dd/yyyy")
private Date date;

@JsonProperty("ship_date")
@JsonFormat(pattern = "MM/dd/yyyy")
private Date ship_date;

@JsonProperty("units_sold")
private String units_sold;

@JsonProperty("unit_price")
private String unit_price;

@JsonProperty("unit_cost")
private String unit_cost;

@JsonProperty("total_revenue")
private String total_revenue;

@JsonProperty("total_cost")
private String total_cost;

@JsonProperty("total_profit")
private String total_profit;

@JsonProperty("links")
private Links links;             

    public Order() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getSales_channel() {
        return sales_channel;
    }

    public void setSales_channel(String sales_channel) {
        this.sales_channel = sales_channel;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getShip_date() {
        return ship_date;
    }

    public void setShip_date(Date ship_date) {
        this.ship_date = ship_date;
    }
    
    public String getUnits_sold() {
        return units_sold;
    }

    public void setUnits_sold(String units_sold) {
        this.units_sold = units_sold;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(String unit_cost) {
        this.unit_cost = unit_cost;
    }

    public String getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(String total_revenue) {
        this.total_revenue = total_revenue;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(String total_profit) {
        this.total_profit = total_profit;
    }
    

    @Override
    public String toString() {
        return "Order{" + "uuid=" + uuid + ", orderId=" + orderId + ", region=" + region + ", country=" + country + ", item_type=" + item_type + ", sales_channel=" + sales_channel + ", priority=" + priority + ", date=" + date + ", ship_date=" + ship_date + ", units_sold=" + units_sold + ", unit_price=" + unit_price + ", unit_cost=" + unit_cost + ", total_revenue=" + total_revenue + ", total_cost=" + total_cost + ", total_profit=" + total_profit + ", links=" + links + '}';
    }

}
