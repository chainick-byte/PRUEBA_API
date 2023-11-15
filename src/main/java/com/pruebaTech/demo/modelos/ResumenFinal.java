/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.modelos;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author igorr
 */
@Entity(name = "RESUMEN")
public class ResumenFinal implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy ="org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "ESTADO_REGISTRO")
    private int estado_registro;
    
    @CreationTimestamp
    @Column(name = "FECHA_CREACION", updatable = false)
    private Timestamp fechaCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;
    
    @ManyToOne
    @JoinColumn(name="ID_TAREA")
    private Tarea tarea;
    
    @Column(name = "CAMPO_RELEVANTE")
    private String campoRelevante;
    
    @Column(name ="CLAVE")
    private String clave;
    
    @Column(name ="VALOR")
    private String valor;

    public ResumenFinal() {
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEstado_registro() {
        return estado_registro;
    }

    public void setEstado_registro(int estado_registro) {
        this.estado_registro = estado_registro;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea idTarea) {
        this.tarea = idTarea;
    }

    public String getCampoRelevante() {
        return campoRelevante;
    }

    public void setCampoRelevante(String campoRelevante) {
        this.campoRelevante = campoRelevante;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "ResumenFinal{" + "id=" + id + ", estado_registro=" + estado_registro + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", idTarea=" + tarea + ", campoRelevante=" + campoRelevante + ", clave=" + clave + ", valor=" + valor + '}';
    }
    
    
    
}
