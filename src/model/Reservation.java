/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author hocin
 */
public class Reservation {
    
    
    int id  ;
    String adult, children, nbjour , nbchambre;
    public Reservation() {
    }
    public Reservation(int id, String adult, String children, String nbjour, String nbchambre) {
        this.id = id;
        this.adult = adult;
        this.children = children;
        this.nbjour = nbjour;
        this.nbchambre = nbchambre;
    }

    public Reservation(String adult, String children, String nbjour, String nbchambre) {
        this.adult = adult;
        this.children = children;
        this.nbjour = nbjour;
        this.nbchambre = nbchambre;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getNbjour() {
        return nbjour;
    }

    public void setNbjour(String nbjour) {
        this.nbjour = nbjour;
    }

    public String getNbchambre() {
        return nbchambre;
    }

    public void setNbchambre(String nbchambre) {
        this.nbchambre = nbchambre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.adult);
        hash = 29 * hash + Objects.hashCode(this.children);
        hash = 29 * hash + Objects.hashCode(this.nbjour);
        hash = 29 * hash + Objects.hashCode(this.nbchambre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.adult, other.adult)) {
            return false;
        }
        if (!Objects.equals(this.children, other.children)) {
            return false;
        }
        if (!Objects.equals(this.nbjour, other.nbjour)) {
            return false;
        }
        if (!Objects.equals(this.nbchambre, other.nbchambre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", adult=" + adult + ", children=" + children + ", nbjour=" + nbjour + ", nbchambre=" + nbchambre + '}';
    }




 
}
 