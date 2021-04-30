/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.ReservationDao;
import model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
class ListData {
    
     /**
     * The data as an observable list of Persons.
     */
    
    private ObservableList<Reservation> res=FXCollections.observableArrayList();

    public ListData() {
        
        ReservationDao pdao=ReservationDao.getInstance();
        res= pdao.displayAll();
        System.out.println(res);
    }
    
    public ObservableList<Reservation> getRes(){
        return res;
    }
   
}
