/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.apache.commons.codec.digest.DigestUtils;

public class user {
    
    int id,role;
    String nom,prenom,email,password;

    public user(int id,int role, String nom, String prenom, String email, String password) {
        this.id = id;
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
       this.password  = DigestUtils.shaHex(password);
        
    }
}
