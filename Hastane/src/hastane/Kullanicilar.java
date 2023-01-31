/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hastane;

import static hastane.Baglanti.con;
import static hastane.Baglanti.statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Muhammet
 */


public class Kullanicilar extends Giris implements IKullanici {
    
    String ad;
    String soyad;
    String unvan;

     public Kullanicilar() {
    }
    
    public Kullanicilar(String unvan) {
        this.unvan = unvan;
    }
  
    public Kullanicilar(String unvan,String sifre, int id) {
        super(unvan, sifre, id);
    }
    
    public String getSoyad(int id) {
        String sorgu= "SELECT * from kullanıcı where id="+id+" ;";
        String soyadS=""; 
         try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while(rs.next())
                soyadS=rs.getString("soyad");
           
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soyadS;
    }
    
    public String getAd(int id) {
        String sorgu= "SELECT * from kullanıcı where id="+id+";";
        String ad1=""; 
         try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while(rs.next())
                ad1=rs.getString("ad");
           
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ad1;
    }
    @Override
    
    public boolean sifredegistirme(int a, String b) {
        String sorgu = "Select * From kullanıcı where id="+a;
        String sifre1="",sifre2=b;
        boolean sonuc=true;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            
            while (rs.next()) {
                               
                sifre1 = rs.getString("sifre");
                
            }
      
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!sifre1.equals(sifre2)) {
        sifre1=sifre2;    
            
        try {
            statement = con.createStatement();
            
            String sorgu1 = "Update kullanıcı Set sifre = '"+sifre1+"' where id="+a;
            statement.executeUpdate(sorgu1);
                        
        sonuc=true;
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        } 
        }
        else{
        sonuc= false;         
        }
        return sonuc;
    }
   
    public void setId(int id,int newId) {
         try {
            String sorgu = "UPDATE kullanıcı SET id=? WHERE id="+id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setInt(1, newId);
            preparedStmt.execute();
         
        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " İd değiştirme başarısız");

        }
    }

    @Override
    public void setAd(int id, String ad) {
        try {
            String sorgu = "UPDATE kullanıcı SET ad=? WHERE id="+id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setString(1, ad);
            preparedStmt.execute();
         
        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Ad değiştirme başarısız");

        }
    }

    @Override
    public void setSoyad(int id, String soyad) {
        try {
            String sorgu = "UPDATE kullanıcı SET soyad=? WHERE id="+id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setString(1, soyad);
            preparedStmt.execute();
         
        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Soyad değiştirme başarısız");

        }
    }
    
}
