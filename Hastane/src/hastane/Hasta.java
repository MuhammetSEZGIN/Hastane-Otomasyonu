/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hastane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;

/**
 *
 * @author Muhammet
 */
public class Hasta extends Kullanicilar implements IHasta {

    public Hasta() {
    }

    @Override
    public String getBoy(int id) {
        String sorgu = "SELECT * FROM hastafizikselbilgiler where hastaId=" + id + ";";
        String boy = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                boy = rs.getString("boy");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return boy;
    }

    @Override
    public String getYas(int id) {
        String sorgu = "SELECT * FROM hastafizikselbilgiler where hastaId=" + id + ";";
        String yas = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                yas = rs.getString("yas");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return yas;
    }

    @Override
    public String getKilo(int id) {
        String sorgu = "SELECT * FROM hastafizikselbilgiler where hastaId=" + id + ";";
        String kilo = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                kilo = rs.getString("kilo");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kilo;
    }

    @Override
    public String getReceteNotu(int id, String tarih) {
        String sorgu = "SELECT * FROM recete where receteid=" + id + " and tarih='" + tarih + "';";
        String doktornot = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                doktornot = rs.getString("doktornotu");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return doktornot;
    }

    @Override
    public String getReceteIlacBilgileri(int id, String tarih) {
        String sorgu = "SELECT * FROM recete where receteid=" + id + " and tarih='" + tarih + "';";
        int ilacid = 0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                ilacid = rs.getInt("ilacid");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ilacid != 0) {

            String sorgu1 = "SELECT * FROM ilaclar where id=" + ilacid + ";";
            String ilacad = "";
            String ilacBilgileri = "";
            try {

                statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sorgu1);

                while (rs.next()) {
                    ilacad = rs.getString("ilacadi");
                    ilacBilgileri = rs.getString("recetelemesekli");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            }

            return ilacad + "\n " + ilacBilgileri;
        } else {
            return "";
        }
    }

    public DefaultTableModel randevuGoster(int id) {
        String sorgu = "SELECT randevu.tarih, kullanıcı.ad,kullanıcı.soyad, doktorlar.klinik, randevu.Randevuİd, randevu.hastaid\n"
                + "FROM ((kullanıcı\n"
                + "INNER JOIN doktorlar ON doktorlar.id = kullanıcı.id)\n"
                + "INNER JOIN randevu ON kullanıcı.id=randevu.doktorid \n"
                + "and kullanıcı.unvan='doktor' and randevu.hastaid=" + id + ");";
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
                if (rs.getInt("hastaid") == id) {
                    saat = rs.getString("tarih");
                    hekimAd = rs.getString("ad");
                    hekimSoyad = rs.getString("soyad");
                    hekimAd = hekimAd + " " + hekimSoyad;
                    klinik = rs.getString("klinik");
                    randevuId = rs.getInt("Randevuİd");
                    Object[] row = {saat, hekimAd, klinik, randevuId};
                    model.addRow(row);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    public DefaultTableModel alinmamisRandevuGoster(int id) {
        String sorgu = "SELECT randevu.tarih, kullanıcı.ad,kullanıcı.soyad, doktorlar.klinik, randevu.Randevuİd, randevu.hastaid\n"
                + "FROM ((kullanıcı\n"
                + "INNER JOIN doktorlar ON doktorlar.id = kullanıcı.id)\n"
                + "INNER JOIN randevu ON kullanıcı.id=randevu.doktorid \n"
                + "and kullanıcı.unvan='doktor' and randevu.hastaid=0);";
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

    @Override
    public void randevuIptal(int id, javax.swing.JTable tablo1, javax.swing.JTable tablo2) {
        int row = tablo1.getSelectedRow();
        String rString = tablo1.getModel().getValueAt(row, 3).toString();
        int randevuId = Integer.parseInt(rString);
        try {
            String sorgu = "UPDATE randevu SET hastaid=0 WHERE Randevuİd= ?";
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setInt(1, randevuId);
            preparedStmt.execute();
            JOptionPane.showMessageDialog(null, "Randevu Silindi");
            tablo1.setModel(randevuGoster(id));
            tablo2.setModel(alinmamisRandevuGoster(id));
        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Silme başarısız");

        }
    }

    public void randevuAl(int id, javax.swing.JTable tablo1, javax.swing.JTable tablo2) {
        int row = tablo1.getSelectedRow();
        String rString = tablo1.getModel().getValueAt(row, 3).toString();
        int randevuId = Integer.parseInt(rString);
        try {
            String sorgu = "UPDATE randevu SET hastaid=? WHERE Randevuİd= ?";
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, randevuId);
            preparedStmt.execute();
            JOptionPane.showMessageDialog(null, "Randevu Alındı");
            tablo2.setModel(randevuGoster(id));
            tablo1.setModel(alinmamisRandevuGoster(id));
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Silme başarısız");

        }
    }

    public DefaultTableModel tumRandevulariGoster(int id) {
        String sorgu = "SELECT randevukontrol.tarih, kullanıcı.ad,kullanıcı.soyad, doktorlar.klinik, randevukontrol.randevuid\n"
                + "FROM ((kullanıcı\n"
                + "INNER JOIN doktorlar ON doktorlar.id = kullanıcı.id)\n"
                + "INNER JOIN randevukontrol ON kullanıcı.id=randevukontrol.doktorid \n"
                + "and kullanıcı.unvan='doktor'\n"
                + ");\n"
                + "";

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

    public DefaultTableModel receteleriGoster(int id) {
        String sorgu = "SELECT * FROM recete WHERE receteid=" + id;
        String tarih = "";
        int doktorid;
        DefaultTableModel model = new DefaultTableModel();
        String[] colname = new String[2];
        colname[0] = "Doktor İd";
        colname[1] = "Tarih";
        model.setColumnIdentifiers(colname);

        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while (rs.next()) {
                doktorid = rs.getInt("doktorid");
                tarih = rs.getString("tarih");
                Object[] row = {doktorid, tarih};
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    @Override
    public void setBoy(int id, int boy) {
        try {
            String sorgu = "UPDATE hastafizikselbilgiler SET boy=? WHERE hastaId=" + id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setInt(1, boy);
            preparedStmt.execute();

        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Ad değiştirme başarısız");

        }
    }

    @Override
    public void setKilo(int id, int kilo) {
        try {
            String sorgu = "UPDATE hastafizikselbilgiler SET kilo=? WHERE hastaId=" + id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setInt(1, kilo);
            preparedStmt.execute();

        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Ad değiştirme başarısız");

        }
    }

    @Override
    public void setYas(int id, int yas) {
        try {
            String sorgu = "UPDATE hastafizikselbilgiler SET yas=? WHERE hastaId=" + id;
            PreparedStatement preparedStmt = con.prepareStatement(sorgu);
            preparedStmt.setInt(1, yas);
            preparedStmt.execute();

        } catch (SQLException ex) {

            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, " Ad değiştirme başarısız");

        }
    }

    public boolean kaydol(int idNo, String adString, String soyadString, String sifre, String unvan1, int boy, int yas, int kilo) {
        try {
            statement = con.createStatement();
            String sorgu1 = "INSERT INTO hastane.kullanıcı (id, ad, soyad, unvan,sifre) \n"
                    + "	VALUES(" + idNo + "," + "'" + adString + "','" + soyadString + "','" + unvan1 + "'," + sifre + ")";
            String sorgu2 = "INSERT INTO hastane.hastafizikselbilgiler (hastaId, boy, kilo, yas) \n"
                    + "	VALUES(" + idNo + "," + boy + "," + kilo + "," + yas + ")";
            statement.executeUpdate(sorgu1);
            statement.executeUpdate(sorgu2);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

    }
}
