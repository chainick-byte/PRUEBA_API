/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebaTech.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author igorr
 */
public class Links {
    
    @JsonProperty("next")
    private String next;
    @JsonProperty("self")
    private String self;
    @JsonProperty("prev")
    private String prev;

    public Links() {
    }

    public Links(String next, String self, String prev) {
        this.next = next;
        this.self = self;
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Links{" + "next=" + next + ", self=" + self + ", prev=" + prev + '}';
    }
    
    
}
