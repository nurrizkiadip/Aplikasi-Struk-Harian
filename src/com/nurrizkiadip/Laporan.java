/**
 * Kelompok 10
 * Kelas    : S1 IF-06-B
 * Anggota  :
 * 1. Naufal Yusuf Kartiko (181020)
 * 2. Nur Rizki Adi Prasetyo (18102064)
 * 3. Wisanti (181020)
 */
package com.nurrizkiadip;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Laporan extends javax.swing.JFrame {

    private int baris; //atribut baris untuk menampung baris ke berapa data yang diklik
    private String tanggal; //menampungkan tanggal belanja pada data yang telah diklik user
    private String nama_toko; //menampungkan nama_toko saat belanja pada data yang telah diklik user
    private String nama_barang; //menampungkan nama barang saat belanja pada data yang telah diklik user
    private String jml_barang; //menampungkan jumlah belanja pada data yang telah diklik user
    private String hargaBarang; //menampungkan harga barang belanja pada data yang telah diklik user

    //method untuk menampilkan total keseluruhan perbelanjaan melalui table
    private void totalBelanjaan() {
        DefaultTableModel total = new DefaultTableModel();

        try {
            Connection conn = Database.koneksi();
            Statement stt;
            PreparedStatement pStt;
            ResultSet resultSet;

            String sqlReset = "TRUNCATE TABLE db_strukharian.HasilTotalBelanja";
            pStt = conn.prepareStatement(sqlReset);
            pStt.execute();

            String sqlSum = "SELECT SUM(totalhargabarang) FROM db_strukharian.daftarbelanja";
            pStt = conn.prepareStatement(sqlSum);
            resultSet = pStt.executeQuery();
            resultSet.next();

            if (Main.cekBelanja() == 1) {
                String sqlTotal = "INSERT INTO HasilTotalBelanja VALUES ('" + resultSet.getString(1) + "')";
                pStt = conn.prepareStatement(sqlTotal);
                pStt.execute();

                String sqlSelect = "SELECT * FROM db_strukharian.HasilTotalBelanja";
                stt = conn.createStatement();
                resultSet = stt.executeQuery(sqlSelect);

                total.addColumn("");
                resultSet.next();

                total.addRow(new Object[]{resultSet.getString(1)});

            } else {
                String sqlTotal = "INSERT INTO HasilTotalBelanja VALUES ('0')";
                pStt = conn.prepareStatement(sqlTotal);
                pStt.execute();

                String sqlSelect = "SELECT * FROM db_strukharian.HasilTotalBelanja";
                stt = conn.createStatement();
                resultSet = stt.executeQuery(sqlSelect);
                resultSet.next();
                total.addColumn("");

                total.addRow(new Object[]{resultSet.getString(1)});
            }

            tblTotalPengeluaran.setModel(total);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }

    }

    //method untuk mencari total data perbelanjaan selama sebulan
    private void cariDataBulan() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String cDari = sdf.format(cariDari.getDate());
        String cSampai = sdf.format(cariSampai.getDate());

        String sqlCari = "SELECT DATE_FORMAT(tanggal, '%M'), SUM(jml_barang) AS Total_JmlBarang, "
                + "SUM(totalhargabarang) AS Total_HargaBarang FROM daftarbelanja WHERE tanggal BETWEEN '"
                + cDari + "' AND '" + cSampai + "' GROUP BY MONTH(tanggal);";

        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("No");
        table.addColumn("Bulan");
        table.addColumn("Total Jumlah Barang");
        table.addColumn("Total Harga Barang");

        try {
            int no = 1;

            Connection conn = Database.koneksi();
            Statement stt = conn.createStatement();
            ResultSet result = stt.executeQuery(sqlCari);

            while (result.next()) {
                table.addRow(new Object[]{
                    no++,
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)
                });
            }

            tblStruk.setModel(table);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan pada saat pencarian data\nError : " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Laporan() throws ParseException {
        initComponents();
        tblStruk.setModel(Main.tampilkanDataTable());

        totalBelanjaan();
        tfSisaLimitBulanan.setText(String.valueOf(Main.limitBulananNonFinal));

        if (Main.waktuSetLimit != null) {
            Main.setUlangLimitBulanan();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStruk = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTotalPengeluaran = new javax.swing.JTable();
        cariSampai = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        cariDari = new com.toedter.calendar.JDateChooser();
        btnBeranda = new javax.swing.JButton();
        tfSisaLimitBulanan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuBeranda = new javax.swing.JMenuItem();
        menuCatat = new javax.swing.JMenuItem();
        menuLaporan = new javax.swing.JMenuItem();
        menuPengaturan = new javax.swing.JMenuItem();
        MenuEdit = new javax.swing.JMenu();
        menuKeluar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Laporan Struk Harian");
        setName("fLaporan"); // NOI18N
        setSize(new java.awt.Dimension(800, 600));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Laporan Struk Harian");

        tblStruk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStruk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStrukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStruk);

        jLabel2.setText("Total Pengeluaran");

        tblTotalPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblTotalPengeluaran);

        cariSampai.setDateFormatString("yyyy-MM-dd");

        jLabel9.setText("sampai");

        jLabel10.setText("dari");

        jLabel8.setText("Cari (tanggal per bulan)");

        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit-converted.png"))); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete-file-converted.png"))); // NOI18N
        btnHapus.setText(" Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search1-converted.png"))); // NOI18N
        btnCari.setText("   Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        cariDari.setDateFormatString("yyyy-MM-dd");

        btnBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/homeflat_converted.png"))); // NOI18N
        btnBeranda.setText("  Beranda");
        btnBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBerandaActionPerformed(evt);
            }
        });

        jLabel3.setText("Sisa Limit Bulanan");

        menuFile.setText("File");

        menuBeranda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/homeflat_converted2.png"))); // NOI18N
        menuBeranda.setText("Beranda");
        menuBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBerandaActionPerformed(evt);
            }
        });
        menuFile.add(menuBeranda);

        menuCatat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCatat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_write_converted.png"))); // NOI18N
        menuCatat.setText("Catat Pengeluaran");
        menuCatat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCatatActionPerformed(evt);
            }
        });
        menuFile.add(menuCatat);

        menuLaporan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Laporan-converted2.png"))); // NOI18N
        menuLaporan.setText("Laporan");
        menuLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanActionPerformed(evt);
            }
        });
        menuFile.add(menuLaporan);

        menuPengaturan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuPengaturan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Setting-converted2.png"))); // NOI18N
        menuPengaturan.setText("Pengaturan");
        menuPengaturan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPengaturanActionPerformed(evt);
            }
        });
        menuFile.add(menuPengaturan);

        MenuBar.add(menuFile);

        MenuEdit.setText("Exit");

        menuKeluar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menuKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit-converted2.png"))); // NOI18N
        menuKeluar.setText("Keluar");
        menuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKeluarActionPerformed(evt);
            }
        });
        MenuEdit.add(menuKeluar);

        MenuBar.add(MenuEdit);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCari)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cariDari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cariSampai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfSisaLimitBulanan)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBeranda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUbah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus)
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSisaLimitBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapus)
                        .addComponent(btnBeranda)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(cariDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cariSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCari)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBeranda, btnHapus, btnUbah});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //method tombol ubah data pada tabel data perbelanjaan
    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        int input;
        if (tanggal != null) {
            UbahDataBelanja ubahData = new UbahDataBelanja(tanggal, nama_toko,
                    nama_barang, jml_barang, hargaBarang);
            ubahData.setVisible(true);

            //memunculkan jendela pencatatan jika memiliki limit bulanan
            if (Main.limitBulananFinal <= 0) {
                input = JOptionPane.showConfirmDialog(null, 
                        "Anda tidak memiliki limit bulanan. Apakah anda ingin mengatur limit bulanan sekarang?\n"
                        + "Jika Ya tekan 'YES', jika Tidak tekan 'NO'", "Peringatan", JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    Laporan.this.setVisible(false);
                    ubahData.setVisible(false);
                    try {
                        new Pengaturan().setVisible(true);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
                    }

                } else {
                    Laporan.this.setVisible(true);
                    ubahData.setVisible(false);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diubah!");
        }

    }//GEN-LAST:event_btnUbahActionPerformed

    //method tombol hapus data pada tabel data perbelanjaan
    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        if (tanggal != null) {
            String sql = "DELETE FROM db_strukharian.daftarbelanja WHERE tanggal = '" + tanggal
                    + "' AND nama_toko = '" + nama_toko + "' AND nama_barang='" + nama_barang
                    + "' AND jml_barang='" + jml_barang
                    + "' AND hargabarang='" + hargaBarang
                    + "' AND totalhargabarang='" + Integer.parseInt(hargaBarang) * Integer.parseInt(jml_barang) + "'";
            try {
                Connection conn = Database.koneksi();
                PreparedStatement stt = conn.prepareStatement(sql);
                stt.execute();

                Main.limitBulananNonFinal = Main.limitBulananFinal - (Main.totalBelanja() - Main.totalBelanjaLama);
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus...");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }

            tblStruk.setModel(Main.tampilkanDataTable());
            tfSisaLimitBulanan.setText(String.valueOf(Main.limitBulananNonFinal));
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
        }


    }//GEN-LAST:event_btnHapusActionPerformed

    //method tombol cari data pada tabel data perbelanjaan
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:

        if (cariDari.getDate() == null && cariSampai.getDate() == null)
            tblStruk.setModel(Main.tampilkanDataTable());
        else
            cariDataBulan();
    }//GEN-LAST:event_btnCariActionPerformed

    //method memilih dan memasukkan data ke textfield pada data tabel yang di pilih
    private void tblStrukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStrukMouseClicked
        // TODO add your handling code here:
        baris = tblStruk.rowAtPoint(evt.getPoint());

        tanggal = tblStruk.getValueAt(baris, 1).toString();
        nama_toko = tblStruk.getValueAt(baris, 2).toString();
        nama_barang = tblStruk.getValueAt(baris, 3).toString();
        jml_barang = tblStruk.getValueAt(baris, 4).toString();
        hargaBarang = tblStruk.getValueAt(baris, 5).toString();
    }//GEN-LAST:event_tblStrukMouseClicked

    //method tombol beranda untuk kembali ke beranda
    private void btnBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBerandaActionPerformed
        // TODO add your handling code here:
        Beranda mainMenu;
        try {
            mainMenu = new Beranda();
            mainMenu.setVisible(true);

            Laporan.this.setVisible(false);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }

    }//GEN-LAST:event_btnBerandaActionPerformed

    //method menu item beranda untuk menuju ke beranda
    private void menuBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBerandaActionPerformed
        // TODO add your handling code here:
        Beranda mainMenu;
        try {
            mainMenu = new Beranda();
            Laporan.this.setVisible(false);
            mainMenu.setVisible(true);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
    }//GEN-LAST:event_menuBerandaActionPerformed

    //method menu item catat untuk menuju ke pencatatan belanjaan
    private void menuCatatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCatatActionPerformed
        // TODO add your handling code here:

        Pencatatan catat = null;
        try {
            catat = new Pencatatan();
            catat.setVisible(true);
            Laporan.this.setVisible(false);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }

        //memunculkan jendela pencatatan jika memiliki limit bulanan
        int input;

        if (Main.limitBulananFinal <= 0) {
            input = JOptionPane.showConfirmDialog(null, "Anda tidak memiliki limit bulanan. Apakah anda ingin mengatur limit bulanan sekarang?\n"
                    + "Jika Ya tekan 'YES', jika Tidak tekan 'NO'", "Peringatan", JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                Laporan.this.setVisible(false);
                catat.setVisible(false);
                try {
                    new Pengaturan().setVisible(true);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
                }

            } else {
                Laporan.this.setVisible(true);
                catat.setVisible(false);
            }
        }
    }//GEN-LAST:event_menuCatatActionPerformed

    //method menu item laporan untuk menuju ke laporan belanja
    private void menuLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanActionPerformed
        // TODO add your handling code here:
        Laporan lap = null;
        try {
            lap = new Laporan();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        Laporan.this.setVisible(false);
        lap.setVisible(true);
    }//GEN-LAST:event_menuLaporanActionPerformed

    //method menu item pengaturan untuk menuju ke pengaturan limit bulanan
    private void menuPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPengaturanActionPerformed
        // TODO add your handling code here:

        try {
            Pengaturan atur = new Pengaturan();
            Laporan.this.setVisible(false);
            atur.setVisible(true);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }

    }//GEN-LAST:event_menuPengaturanActionPerformed

    //method menu item keluar untuk mengakhiri running program
    private void menuKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKeluarActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_menuKeluarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu MenuEdit;
    private javax.swing.JButton btnBeranda;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnUbah;
    private com.toedter.calendar.JDateChooser cariDari;
    private com.toedter.calendar.JDateChooser cariSampai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuBeranda;
    private javax.swing.JMenuItem menuCatat;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuKeluar;
    private javax.swing.JMenuItem menuLaporan;
    private javax.swing.JMenuItem menuPengaturan;
    private javax.swing.JTable tblStruk;
    private javax.swing.JTable tblTotalPengeluaran;
    private javax.swing.JTextField tfSisaLimitBulanan;
    // End of variables declaration//GEN-END:variables
}
