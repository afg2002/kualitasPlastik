/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tampilan;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import helper.koneksi;

/**
 *
 * @author afgha
 */
public final class HitungRanking extends javax.swing.JDialog {
    private Connection conn = new koneksi().getConnection();
    int jumlahKriteria = 3;
    JTextField[][] textFields = new JTextField[jumlahKriteria+1][jumlahKriteria+1];
    DecimalFormat numberFormat = new DecimalFormat("0.000");
    private DefaultTableModel tablemodel;
//    00,10,20

    

    
    
    protected void datatable(){
        Object[] Baris = {"No.","Nama Pemilihan", "Nilai Bobot"};
        tablemodel = new DefaultTableModel(null, Baris);
        tRanking.setModel(tablemodel);
        String sql = "SELECT nama_pemilihan, nilai_bobot FROM pemilihan INNER JOIN ranking ON pemilihan.id_pemilihan = ranking.id_pemilihan ORDER BY ranking.id_pemilihan";
        try{
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            int no = 0;
            while(res.next()){
                no++;
                String a = res.getString("nama_pemilihan");
                String b= res.getString("nilai_bobot");
   
                String[] data = {String.valueOf(no),a,b};
                tablemodel.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HitungRanking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     void getPvKriteria(){
         try{
            String sql = "SELECT * FROM `pv_kriteria` ORDER by kode_kriteria ASC";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while(rs.next()){
                String a =  rs.getString("kode_kriteria");
                int aa = Integer.parseInt(a.substring(1));
                int aaa = aa-1;
                double b  = rs.getDouble("nilai_prioritas");
                textFields[aaa][0].setText(String.valueOf(b));
                aaa++;
            }

        }   catch (SQLException ex) {  
                Logger.getLogger(HitungRanking.class.getName()).log(Level.SEVERE, null, ex);
            }  
     }
     void selectpvAlternatif(){
        try{
            String sql = "SELECT * FROM `pv_alternatif` ORDER BY id_pemilihan,kode_kriteria;";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            
            
            while(rs.next()){
                int a  = rs.getInt("id_pemilihan");
                String b =  rs.getString("kode_kriteria");
                int bb = Integer.parseInt(b.substring(1));
                double c = rs.getDouble("nilai_prioritas");
                
                textFields[bb-1][a].setText(String.valueOf(c));
//                System.out.println(bb-1 + " " + a + "=" + c);
            }

        }   catch (SQLException ex) {  
                Logger.getLogger(HitungRanking.class.getName()).log(Level.SEVERE, null, ex);
            }  
}   
    void getNamaKriteria(){
        String sql  = "SELECT nama_kriteria FROM `kriteria`";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String a = rs.getString("nama_kriteria");
                JLabel labelBaris = new JLabel(a);
                labelBaris.setBorder(new EmptyBorder(0, 0, 110, 0));
                pBarisKriteria.add(labelBaris);
            }
            JLabel total = new JLabel("Total");
            pBarisKriteria.add(total);
        } catch (SQLException ex) {
            Logger.getLogger(HitungRanking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void getNamaAlternatif(){
        String sql  = "SELECT nama_pemilihan FROM `pemilihan`";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            JLabel priority = new JLabel("Priority Vector (rata-rata)");
            priority.setBorder(new EmptyBorder(0, 0, 0, 70));
            pKolomAlternatif.add(priority);
            while(rs.next()){
                String a = rs.getString("nama_pemilihan");
                JLabel labelKolom = new JLabel(a);
                labelKolom.setBorder(new EmptyBorder(0, 40, 0, 80));
                pKolomAlternatif.add(labelKolom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HitungRanking.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
     void makeTextFieldPerbandingan(){
        for (int i = 0; i < jumlahKriteria+1; i++) {
            for (int j = 0; j < jumlahKriteria+1; j++) {
                textFields[i][j] = new JTextField();
                textFields[i][j].setEnabled(false);
                pPerhitungan.add(textFields[i][j]);
                
            }
        }
    }
    
     void hitungBobotNilai(){
         double totalNilaiBobot = 0;
         for (int i = 0; i < jumlahKriteria; i++) {
             for (int j = 0; j < jumlahKriteria; j++) {
//                 System.out.println(j+""+ (i+1));
//                 System.out.println(j+""+ 0);
//                 System.out.println(i);
                 double pvAlternatif = Double.parseDouble(textFields[j][i+1].getText());
                 System.out.println(pvAlternatif);
                 double pvKriteria = Double.parseDouble(textFields[j][0].getText());
//                 System.out.println(pvKriteria);
                 double nilaiBobot =  pvKriteria*pvAlternatif;
                 totalNilaiBobot+= nilaiBobot;
                 textFields[jumlahKriteria][i+1].setText(String.valueOf(numberFormat.format(totalNilaiBobot)));
                 if(j == jumlahKriteria-1){
                     totalNilaiBobot = 0;
                 }
             }
             try{
                 String sql = "INSERT INTO ranking VALUES (?,?) ON DUPLICATE KEY UPDATE nilai_bobot = ? ";
                 PreparedStatement pst = conn.prepareStatement(sql);
                 pst.setString(1, String.valueOf(i+1));
                 pst.setString(2, textFields[jumlahKriteria][i+1].getText());
                 pst.setString(3, textFields[jumlahKriteria][i+1].getText());
                 pst.executeUpdate();
                 
             } catch (SQLException ex) {
                 Logger.getLogger(HitungRanking.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         datatable();
     }
     
    
    public HitungRanking(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        makeTextFieldPerbandingan();
        getNamaKriteria();
        getNamaAlternatif();
//        datatable();
        pBarisKriteria.setLayout(new BoxLayout(pBarisKriteria,BoxLayout.Y_AXIS)); 
        pKolomAlternatif.setLayout(new BoxLayout(pKolomAlternatif,BoxLayout.X_AXIS)); 
        pKolomAlternatif.setPreferredSize(new Dimension(608, 43));
        pBarisKriteria.setPreferredSize(new Dimension(100, 450));
        pPerhitungan.setLayout(new GridLayout(jumlahKriteria+1, jumlahKriteria+1));
        textFields[jumlahKriteria][0].setEnabled(false);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        pPerhitungan = new javax.swing.JPanel();
        pBarisKriteria = new javax.swing.JPanel();
        pKolomAlternatif = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tRanking = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Tampilkan Ranking");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pPerhitungan.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout pPerhitunganLayout = new javax.swing.GroupLayout(pPerhitungan);
        pPerhitungan.setLayout(pPerhitunganLayout);
        pPerhitunganLayout.setHorizontalGroup(
            pPerhitunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pPerhitunganLayout.setVerticalGroup(
            pPerhitunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pBarisKriteria.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout pBarisKriteriaLayout = new javax.swing.GroupLayout(pBarisKriteria);
        pBarisKriteria.setLayout(pBarisKriteriaLayout);
        pBarisKriteriaLayout.setHorizontalGroup(
            pBarisKriteriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pBarisKriteriaLayout.setVerticalGroup(
            pBarisKriteriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        pKolomAlternatif.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout pKolomAlternatifLayout = new javax.swing.GroupLayout(pKolomAlternatif);
        pKolomAlternatif.setLayout(pKolomAlternatifLayout);
        pKolomAlternatifLayout.setHorizontalGroup(
            pKolomAlternatifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );
        pKolomAlternatifLayout.setVerticalGroup(
            pKolomAlternatifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        jLabel1.setText("Overall Composite Height");

        tRanking.setForeground(new java.awt.Color(204, 0, 153));
        tRanking.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tRanking);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Hasil Ranking");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(637, 637, 637)
                .addComponent(jButton1)
                .addContainerGap(403, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(pBarisKriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(pPerhitungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pKolomAlternatif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(158, 158, 158))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pKolomAlternatif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pBarisKriteria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pPerhitungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         selectpvAlternatif();
         getPvKriteria();
         hitungBobotNilai();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HitungRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HitungRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HitungRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HitungRanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HitungRanking hrf = new HitungRanking(new javax.swing.JFrame(), true);
                hrf.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pBarisKriteria;
    private javax.swing.JPanel pKolomAlternatif;
    private javax.swing.JPanel pPerhitungan;
    private javax.swing.JTable tRanking;
    // End of variables declaration//GEN-END:variables
}
