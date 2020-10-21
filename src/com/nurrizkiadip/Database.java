/**
 * Kelompok 10
 * Kelas    : S1 IF-06-B
 * Anggota  :
 * 1. Naufal Yusuf Kartiko (181020)
 * 2. Nur Rizki Adi Prasetyo (18102064)
 * 3. Wisanti (181020)
 */
package com.nurrizkiadip;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
        
public class Database {
    
    //atribut untuk menyimpan koneksi ke database
    private static Connection mySQL;
    
    //langkah-langkah proses koneksi dari java ke database
    public static Connection koneksi() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/db_strukharian?"
                + "useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String pass = "";
        
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            mySQL = DriverManager.getConnection(url, user, pass);

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Kesalahan pada koneksi database\n"
                    + "Error : " + e.getMessage(), "Kesalahan Database", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
        return mySQL;
    }
}
