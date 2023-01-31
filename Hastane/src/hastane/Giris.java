/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hastane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muhammet
 */
interface IGiris {

    int getId(int id);
    String getSifre(int id);
    boolean Baglan();
}

interface IKullanici{
    void setAd(int id, String ad);
    void setSoyad(int id, String soyad);
    void setId(int id, int newId);
    boolean sifredegistirme(int a, String b);
    String getAd(int id);
    String getSoyad(int id);
}

interface IHasta {
    String getBoy(int id);
    String getYas(int id);
    String getKilo(int id);
    String getReceteNotu(int id, String tarih);
    String getReceteIlacBilgileri(int id, String tarih);
    void setBoy(int id, int boy);
    void setKilo(int id, int kilo);
    void setYas(int id, int yas);
    public boolean kaydol(int idNo, String adString, String soyadString, String sifre, String unvan1, int boy,int yas,int kilo);
    public void randevuIptal(int id, javax.swing.JTable tablo1, javax.swing.JTable tablo2);
    public void randevuAl(int id, javax.swing.JTable tablo1, javax.swing.JTable tablo2);
}


interface IDoktor {
   void receteYaz(int ilacid, int hastaId, String doktornotu, String tarih,int doktorid);
   void randevuEkle(int id, String tarih);
   void randevuSil(int randevuid);
}

abstract public class Giris extends Baglanti implements IGiris {
    
    public String unvan;
    public String sifre;
    int id;

    public Giris() {
    }

    
    public Giris(String unvan, String sifre, int id) {
        this.unvan = unvan;
        this.sifre = sifre;
        this.id = id;
    }
   
    public String getUnvan() {
        return unvan;
    }
    
    @Override
    public int getId( int id) {
        String sorgu= "SELECT * FROM kullanıcı where id="+id+";";
        int inputId=0; 
         try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            if(rs.next())
                inputId=rs.getInt("id");
           
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inputId;
    }

    @Override
    public String getSifre(int id) {
       String sorgu= "SELECT * FROM kullanıcı where id="+id+";";
        String sifre=""; 
         try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            if(rs.next())
                sifre=rs.getString("sifre");
           
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sifre;
    }
    
    @Override
    public boolean Baglan() {
        String sorgu = "select * from kullanıcı where id='" + this.id + "' and sifre='" + this.sifre + "' and unvan='" + this.unvan +"'";
        boolean sonuc = false;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            if (rs.next()) {
                sonuc = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sonuc;
    }
}

