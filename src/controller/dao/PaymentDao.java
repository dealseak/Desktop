/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;


import helpers.ConnexionSingleton;
import model.Payment;
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
public class PaymentDao implements Idao<Payment>{
    
    private static PaymentDao instance;
    private Statement st;
    private ResultSet rs;
    
    private PaymentDao() {
        ConnexionSingleton cs=ConnexionSingleton.getInstance();
        try {
            st=cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PaymentDao getInstance(){
        if(instance==null) 
            instance=new PaymentDao();
        return instance;
    }
    @Override
    public void insert(Payment o) {
        String req="insert into payment (firstname,lastname,email,phone,card_number) values ('" +o.getFirstname()+ "','"+o.getLastname()+ "','" +o.getEmail()+ "','" +o.getPhone()+ "','" +o.getCard_number()+ "')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   


    @Override
    public ObservableList<Payment> displayAll() {
        String req="select * from payment";
        ObservableList<Payment> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Payment p=new Payment();
                 p.setId(rs.getInt("id"));
                p.setFirstname(rs.getString("firstname"));
                p.setLastname(rs.getString("lastname"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setCard_number(rs.getString("card_number"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Payment> displayAllList() {
        String req="select * from payment";
        List<Payment> list=new ArrayList<>();
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Payment p=new Payment();
                p.setId(rs.getInt("id"));
                p.setFirstname(rs.getString("firstname"));
                p.setLastname(rs.getString("lastname"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setCard_number(rs.getString("card_number"));
                
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    @Override
    public Payment displayById(int id) {
           String req="select * from payment where id ="+id;
           Payment p=new Payment();
        try {
            rs=st.executeQuery(req);
           // while(rs.next()){
            rs.next();
                p.setId(rs.getInt("id"));
                p.setFirstname(rs.getString("firstname"));
                p.setLastname(rs.getString("lastname"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setCard_number(rs.getString("card_number"));

                //}  
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    return p;
    }

 
}