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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Main {

    //menampung limit bulanan yang ditetapkan(nilai tidak berubah)
    public static int limitBulananFinal;
    
    //menampung limit bulanan sebagai patokan sisa limit bulanan yang dimiliki (alat perhitungan)
    public static int limitBulananNonFinal;
    
    //menampung nilai sebagai pengingat batas limit bulanan
    public static int pengingatLimitBulanan;
    
    //menampung tanggal kapan user mengatur limit bulanan
    public static String waktuSetLimit;
    
    //menampung tanggal kapan user mengatur limit bulanan
    public static int totalBelanjaLama;

    public static void main(String[] args) throws ParseException, Exception {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Beranda().setVisible(true);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Kesalahan pada Running Beranda\n"
                            + "Error : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //mengembalikan pengaturan limit bulanan yang user pernah inputkan
        setBackPengaturan();

    }

    //method untuk mengembalikan limit bulanan yang pernah diatur user
    public static void setBackPengaturan() {
        String sqlSelect = "SELECT * FROM db_strukharian.pengaturan";

        try {
            Connection conn = Database.koneksi();
            Statement stt = conn.createStatement();
            ResultSet resultSet;

            
            String sqlCek = "SELECT COUNT(tanggal) FROM db_strukharian.daftarbelanja";
            PreparedStatement pStt = conn.prepareStatement(sqlCek);
            resultSet = pStt.executeQuery();
            resultSet.next();

            if (Integer.parseInt(resultSet.getString(1)) != 0) {
                resultSet = stt.executeQuery(sqlSelect);
                resultSet.next();
                
                Main.limitBulananFinal = Integer.parseInt(resultSet.getString(1));
                Main.limitBulananNonFinal = Integer.parseInt(resultSet.getString(2));
                Main.pengingatLimitBulanan = Integer.parseInt(resultSet.getString(3));
                Main.waktuSetLimit = resultSet.getString(4);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada set kembali ke pengaturan\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }

    }

    //method untuk menampilkan data belanja dalam tabel
    public static DefaultTableModel tampilkanDataTable() {
        DefaultTableModel table = new DefaultTableModel();

        table.addColumn("No");
        table.addColumn("Tanggal");
        table.addColumn("Nama Toko");
        table.addColumn("Nama Barang");
        table.addColumn("Jumlah Barang");
        table.addColumn("Harga Barang");
        table.addColumn("Total Harga Barang");

        try {
            int no = 1;
            String sql = "SELECT * FROM db_strukharian.daftarbelanja";

            Connection conn = Database.koneksi();
            Statement stt = conn.createStatement();
            ResultSet result = stt.executeQuery(sql);

            while (result.next()) {
                table.addRow(new Object[]{
                    no++,
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada menampilkan data tabel\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }

        return table;
    }

    //method totalBelanja untuk menghitung total belanja
    public static int totalBelanja() {
        String sqlSum = "SELECT SUM(totalhargabarang) FROM daftarbelanja;";
        int sum = 0;
        try {
            Connection conn = Database.koneksi();
            PreparedStatement pStt = conn.prepareStatement(sqlSum);
            ResultSet result = pStt.executeQuery();

            result.next();
            if (result.getString(1) != null) {
                sum = Integer.parseInt(result.getString(1));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada Perhitungan Total Belanja\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        return sum;
    }

    //method cekBelanja untuk memeriksa riwayat data belanja 
    public static int cekBelanja() {
        String sql = "SELECT COUNT(tanggal) FROM db_strukharian.daftarbelanja";

        try {
            Connection conn = Database.koneksi();
            PreparedStatement pStt = conn.prepareStatement(sql);
            ResultSet result = pStt.executeQuery();

            result.next();
            if (Integer.parseInt(result.getString(1)) != 0) {
                return 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada Cek Belanja\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    //method untuk menghapus permanen riwayat perbelanjaan
    public static void resetData(int input) {
        String sqlReset = "TRUNCATE TABLE db_strukharian.daftarbelanja";

        try {
            Connection conn = Database.koneksi();
            PreparedStatement stt = conn.prepareStatement(sqlReset);

            if (cekBelanja() == 1) {
                if (input == 0) {
                    stt.execute();
                    JOptionPane.showMessageDialog(null, "Reset Data Berhasil...", "Sukses", JOptionPane.PLAIN_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada Reset Data\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    //method untuk memasukkan kembali pengaturan limit bulanan yang pernah user masukkan ke program
    public static void setUlangLimitBulanan() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDateTime.now();
        String waktuSaatIni = dtf.format(currentDate);
        
        try {
            //Setting the date to the given date
            c1.setTime(sdf.parse(Main.waktuSetLimit));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada Set Ulang Limit Bulanan\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }

        //Number of Days to add
        c1.add(Calendar.DAY_OF_MONTH, 30);
        //Date after adding the days to the given date
        String tglSetLimitBaru = sdf.format(c1.getTime());

        Date d1 = sdf.parse(tglSetLimitBaru);
        Date d2 = sdf.parse(waktuSaatIni);

        if (d2.compareTo(d1) > 0) {
            Main.limitBulananNonFinal = Main.limitBulananFinal;
            totalBelanjaLama += totalBelanja();

            Main.waktuSetLimit = tglSetLimitBaru;
        }
    }
}
