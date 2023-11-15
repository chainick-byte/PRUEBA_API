/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.modelos;

import com.pruebaTech.demo.utils.Constantes;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author igorr
 */
@Entity
@Table(name = "TAREA")
public class Tarea implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_TAREA")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy ="org.hibernate.id.UUIDGenerator")
    private String idTarea;
    
    @CreationTimestamp
    @Column(name = "FECHA_CREACION", updatable = false)
    private Timestamp fechaCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;
    
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro = Constantes.ESTADO_ACTIVO;
    
    @Column(name = "FUNCIONAMIENTO")
    private String funcionamiento;
    
    @Column(name ="URL", length=1024)
    private String url;
    
    @Column(name = "TIEMPO")
    private Long tiempo;
    
    @Column(name = "ERROR")
    private String hayError;
    
    @OneToMany( mappedBy =  "idTarea")
    private List<Libro> libros;
    
    @OneToMany (mappedBy = "tarea")
    private List<ResumenFinal> resumenFinal;

    
    public Tarea() {
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getFuncionamiento() {
        return funcionamiento;
    }

    public void setFuncionamiento(String funcionamiento) {
        this.funcionamiento = funcionamiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
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

    public String getHayError() {
        return hayError;
    }

    public void setHayError(String hayError) {
        this.hayError = hayError;
    }

    public List<ResumenFinal> getResumenFinal() {
        return resumenFinal;
    }

    public void setResumenFinal(List<ResumenFinal> resumenFinal) {
        this.resumenFinal = resumenFinal;
    }
    
    @Override
    public String toString() {
        return "Tarea{" + "idTarea=" + idTarea + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", estadoRegistro=" + estadoRegistro + ", funcionamiento=" + funcionamiento + ", url=" + url + ", tiempo=" + tiempo + ", hayError=" + hayError + ", libros=" + libros + '}';
    }
    
}
