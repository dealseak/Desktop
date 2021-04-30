/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;


import helpers.ConnexionSingleton;
import model.Reservation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wiemhjiri
 */
public class ReservationDao implements Idao<Reservation>{
    
    private static ReservationDao instance;
    private Statement st;
    private ResultSet rs;
    
    private ReservationDao() {
        ConnexionSingleton cs=ConnexionSingleton.getInstance();
        try {
            st=cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ReservationDao getInstance(){
        if(instance==null) 
            instance=new ReservationDao();
        return instance;
    }
    @Override
    public void insert(Reservation o) {
        String req="insert into reservation (adult,children,nbjour,nbchambre) values ('" +o.getAdult()+ "','"+o.getChildren()+ "','" +o.getNbjour()+ "','" +o.getNbchambre()+ "')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   



    @Override
    public ObservableList<Reservation> displayAll() {
        String req="select * from reservation";
        ObservableList<Reservation> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Reservation p=new Reservation();
                 p.setId(rs.getInt("id"));
                p.setAdult(rs.getString("adult"));
                p.setChildren(rs.getString("children"));
                p.setNbjour(rs.getString("nbjour"));
                p.setNbchambre(rs.getString("nbchambre"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Reservation> displayAllList() {
        String req="select * from reservation";
        List<Reservation> list=new ArrayList<>();
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Reservation p=new Reservation();
                 p.setId(rs.getInt("id"));
                p.setAdult(rs.getString("adult"));
                p.setChildren(rs.getString("children"));
                p.setNbjour(rs.getString("nbjour"));
                p.setNbchambre(rs.getString("nbchambre"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    @Override
    public Reservation displayById(int id) {
           String req="select * from reservation where id ="+id;
           Reservation p=new Reservation();
        try {
            rs=st.executeQuery(req);
           // while(rs.next()){
            rs.next();
                  p.setId(rs.getInt("id"));
                p.setAdult(rs.getString("adult"));
                p.setChildren(rs.getString("children"));
                p.setNbjour(rs.getString("nbjour"));
                p.setNbchambre(rs.getString("nbchambre"));
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    return p;
    }



 
}