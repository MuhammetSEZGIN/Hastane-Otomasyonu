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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Muhammet
 */
public class Doktor extends Kullanicilar implements IDoktor {

    private int id;
    private String klinik;

    public Doktor() {
    }

    public Doktor(int id) {
        this.id = id;
    }

    public Doktor(int id, String klinik) {
        this.id = id;
        this.klinik = klinik;
    }

    public DefaultTableModel tumRandevulariGoster(int id) {
        String sorgu = "SELECT randevu.tarih, kullanıcı.ad,kullanıcı.soyad, doktorlar.klinik, randevu.Randevuİd, randevu.hastaid\n"
                + "FROM ((kullanıcı\n"
                + "INNER JOIN doktorlar ON doktorlar.id = kullanıcı.id)\n"
                + "INNER JOIN randevu ON kullanıcı.id=randevu.doktorid \n"
                + "and kullanıcı.unvan='doktor' and randevu.doktorid=" + id + ");";

        DefaultTableModel model = new DefaultTableModel();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            String[] colname = new String[4];
            colname[0] = "Tarih";
            colname[1] = "Doktor";
            colname[2] = "Klinik";
            colname[3] = "Randevu Numarası";
            model.setColumnIdentifiers(colname);
            String saat;
            String hekimAd, hekimSoyad, klinik;
            int randevuId;
            while (rs.next()) {
                saat = rs.getString("tarih");
                hekimAd = rs.getString("ad");
                hekimSoyad = rs.getString("soyad");
                hekimAd = hekimAd + " " + hekimSoyad;
                klinik = rs.getString("klinik");
                randevuId = rs.getInt("Randevuİd");
                Object[] row = {saat, hekimAd, klinik, randevuId};
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    public String getKlinik(int id) {

        String sorgu = "SELECT * from doktorlar where id=" + id + " ;";
        String klinikString = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while (rs.next()) {
                klinikString = rs.getString("klinik");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return klinikString;

    }

    public void setKlinik(int id, String kliString) {
        try {
            String sorgu = "UPDATE doktorlar SET klinik=? WHERE id=" + id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setString(1, kliString);
            preparedStmt.execute();
        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Klinik adı değiştirme başarısız");
        }
    }

    @Override
    public void receteYaz(int ilacid, int hastaId, String doktornotu, String tarih, int doktorid) {

        try {
            statement = con.createStatement();

            String sorgu = "INSERT INTO hastane.recete (receteid, doktornotu, tarih, ilacid,doktorid) \n"
                    + "	VALUES(" + "'" + hastaId + "'," + "'" + doktornotu + "','" + tarih + "'," + ilacid + "," + doktorid + ")";

            statement.executeUpdate(sorgu);
            JOptionPane.showMessageDialog(null, "Reçete ekleme başarılı");
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Reçete ekleme başarısız");
        }
    }

    @Override
    public void randevuEkle(int id, String tarih) {
        try {
            statement = con.createStatement();
            String sorgu = "INSERT INTO hastane.randevu (doktorid, tarih, hastaid) \n"
                    + "VALUES (" + id + " ,'" + tarih + "', 0)";
            statement.executeUpdate(sorgu);

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Randevu ekleme başarısız lütfen randevu sırası mevcut olan değerlerden farklı giriniz");

        }
    }

    @Override
    public void randevuSil(int randevuid) {
        try {
            statement = con.createStatement();
            String sorgu = "DELETE FROM randevu where Randevuİd=" + randevuid + ";\n"
                    + "";
            statement.executeUpdate(sorgu);
            JOptionPane.showMessageDialog(null, " Silme başarılı");

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Silme başarısız");

        }
    }

    public DefaultTableModel hastalariGoruntule(int id) {
        String sorgu = "SELECT ad,soyad,hastaid FROM\n"
                + "kullanıcı\n"
                + "INNER JOIN randevu on kullanıcı.id=randevu.hastaid\n"
                + "WHERE randevu.doktorid=" + id + ";";

        DefaultTableModel model = new DefaultTableModel();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            String[] colname = new String[3];
            colname[0] = "Ad";
            colname[1] = "Soyad";
            colname[2] = "Hasta İd";
            model.setColumnIdentifiers(colname);

            String hastaad, hastaSoyad;
            int hastaId;
            while (rs.next()) {

                hastaad = rs.getString("ad");
                hastaSoyad = rs.getString("soyad");
                hastaId = rs.getInt("hastaid");
                Object[] row = {hastaId, hastaad, hastaSoyad};
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    void ilaclariGoster(JComboBox temp) {

        String sorgu = "SELECT * from ilaclar ORDER BY id ASC";
        String ad = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while (rs.next()) {
                ad = rs.getString("ilacadi");
                temp.addItem(ad);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
