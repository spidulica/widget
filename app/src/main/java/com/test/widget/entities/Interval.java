package com.test.widget.entities;

import java.io.Serializable;

/**
 * Created by Spidulica on 26-Mar-17.
 */

public class Interval implements Serializable {
    private String grupa;
    private String materie;
    private String profesor;
    private String locatie;
    private String oraInceput;
    private String oraSfarsit;
    private Long id;
    private int an;
    private String zi;

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(String oraInceput) {
        this.oraInceput = oraInceput;
    }

    public String getOraSfarsit() {
        return oraSfarsit;
    }

    public void setOraSfarsit(String oraSfarsit) {
        this.oraSfarsit = oraSfarsit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "grupa='" + grupa + '\'' +
                ", materie='" + materie + '\'' +
                ", profesor='" + profesor + '\'' +
                ", locatie='" + locatie + '\'' +
                ", oraInceput='" + oraInceput + '\'' +
                ", oraSfarsit='" + oraSfarsit + '\'' +
                ", id=" + id +
                ", an=" + an +
                ", zi='" + zi + '\'' +
                '}';
    }
}
