/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hastane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Muhammet
 */
abstract public class Baglanti {
    private String kullanici="root";
    private String parola="root";
    String url="jdbc:mysql://localhost:3306/hastane?zeroDateTimeBehavior=CONVERT_TO_NULL";
    static Connection con = null;
    static Statement statement=null;
    
    public Baglanti(){
        try {
            con = DriverManager.getConnection(url, kullanici, parola);
            System.out.println("Bağlantı Başarılı...");    
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
    }
}
