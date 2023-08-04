/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package tampilan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import helper.koneksi;
import java.sql.Statement;

/**
 *
 * @author afgha
 */
public final class DataAlternatif extends javax.swing.JDialog {
    private final Connection conn = new koneksi().getConnection();
    JTextField[][] textFields = new JTextField[6][5];
    JTextField[][] textFields2 = new JTextField[8][8];
    DataHasilNilaiKriteriaAlternatif hnka = new DataHasilNilaiKriteriaAlternatif(null, true);
    int jumlahAlternatif = 0;
    double randomIndex=0;
    DecimalFormat numberFormat = new DecimalFormat("0.000");
    String jumlahAlternatifID;
    ArrayList<String> arrIdPem = new ArrayList<>();
    DataAlternatif(Object object, boolean b, String name) {
        jumlahAlternatifID = name;
    }
    // Fungsi untuk menginisialisasi variabel kriteria berdasarkan data dalam tabel kriteria
    private void initAlternatif() {
        String sql = "SELECT COUNT(*) AS jumlah_kriteria FROM kriteria";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                jumlahAlternatif = res.getInt("jumlah_kriteria");
                System.out.println(jumlahAlternatif);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengambil data kriteria!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
       
    }
    
     void disableMatriks(){
       for (int i = 0; i < jumlahAlternatif; i++) {
           textFields[i][i].setText("1");
           textFields[i][i].setEnabled(false);
           textFields[i][i].setForeground(Color.red);
           textFields[jumlahAlternatif][i].setEnabled(false);
       }
       
       
        int n = jumlahAlternatif-1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                textFields[i][j].setEnabled(false);
            }
        }
      
    }
     
    void getNamaAlternatif(){
        String sql  = "SELECT id_pemilihan,nama_pemilihan FROM `pemilihan`";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String a = rs.getString("nama_pemilihan");
                String b = rs.getString("id_pemilihan");
                arrIdPem.add(b);
                JLabel labelBaris = new JLabel(a);
                JLabel labelKolom = new JLabel(a);
                JLabel labelBarisNK = new JLabel(a);
                JLabel labelKolomNK = new JLabel(a);
                labelBaris.setBorder(new EmptyBorder(0,70,0,110));
                labelKolom.setBorder(new EmptyBorder(0,0,120,0));
                pBarisAlternatif.add(labelBaris);
                pKolomAlternatif.add(labelKolom);
                labelBarisNK.setBorder(new EmptyBorder(0,70,0,90));
                labelKolomNK.setBorder(new EmptyBorder(0,0,180,0));
                hnka.KolomNK.add(labelBarisNK);
                hnka.barisNK.add(labelKolomNK);
                
            }
            JLabel labelTotal = new JLabel("Total");
            pKolomAlternatif.add(labelTotal);
            JLabel labelJumlahNK = new JLabel("Jumlah");
            labelJumlahNK.setBorder(new EmptyBorder(0, 0, 0, 65));
            JLabel labelprioritasNK = new JLabel("Prioritas");
            hnka.KolomNK.add(labelJumlahNK);
            hnka.KolomNK.add(labelprioritasNK);
        } catch (SQLException ex) {
            Logger.getLogger(DataAlternatif.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
    
    void makeTextFieldPerbandingan(){
        for (int i = 0; i < jumlahAlternatif+1; i++) {
            for (int j = 0; j < jumlahAlternatif; j++) {
                textFields[i][j] = new JTextField();
                pPerbandingan.add(textFields[i][j]);
                
            }
        }
    }
    
   
    void makeTextFieldNilaiKriteria(){
        //horizontal
        for (int i = 0; i < jumlahAlternatif; i++) {
            //vertical
            for (int j = 0; j < jumlahAlternatif+2; j++) {
                textFields2[i][j] = new JTextField();
                textFields2[i][j].setEnabled(false);
                hnka.pNK.add(textFields2[i][j]);
            }
        }
    }
    void setRandomIndex(){
       switch (jumlahAlternatif) {
           case 1:
           case 2:
               randomIndex = 0.00;
               break;
           case 3:
               randomIndex =  0.58;
               break;
           case 4:
               randomIndex =  0.90;
               break;
           case 5:
               randomIndex =  1.12;
               break;
           case 6:
               randomIndex =  1.24;
               break;
           case 7:
               randomIndex =  1.32;
               break;
           case 8:
               randomIndex =  1.41;
               break;
           case 9:
               randomIndex =  1.45;
               break;
           case 19:
               randomIndex =  1.49;
               break;
           default:
               break;
       }
   }
    
    public DataAlternatif(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initAlternatif();
        makeTextFieldPerbandingan();
        getNamaAlternatif();
        disableMatriks();
        makeTextFieldNilaiKriteria();
        setRandomIndex();
        System.out.println(jumlahAlternatifID);

        tRI1.setText(String.valueOf(randomIndex));
        pBarisAlternatif.setPreferredSize(new Dimension(585, 50));
        pKolomAlternatif.setPreferredSize(new Dimension (101,388)); 
        hnka.KolomNK.setPreferredSize(new Dimension(800, 50));
        hnka.barisNK.setPreferredSize(new Dimension (120,500)); 
        
        pBarisAlternatif.setLayout(new BoxLayout(pBarisAlternatif, BoxLayout.X_AXIS));
        pKolomAlternatif.setLayout(new BoxLayout(pKolomAlternatif, BoxLayout.Y_AXIS));
        hnka.KolomNK.setLayout(new BoxLayout(hnka.KolomNK, BoxLayout.X_AXIS));
        hnka.barisNK.setLayout(new BoxLayout(hnka.barisNK, BoxLayout.Y_AXIS));
        pPerbandingan.setPreferredSize(new Dimension(585,388));
        pPerbandingan.setLayout(new GridLayout(jumlahAlternatif+1, jumlahAlternatif));
        hnka.pNK.setLayout(new GridLayout(jumlahAlternatif, jumlahAlternatif));
        hnka.pNK.setPreferredSize(new Dimension(800, 550));
    
    }
        

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pBarisAlternatif = new javax.swing.JPanel();
        pKolomAlternatif = new javax.swing.JPanel();
        pPerbandingan = new javax.swing.JPanel();
        bHitung = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tCR = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tCI = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tRI1 = new javax.swing.JTextField();
        tstatusCR = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelBro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Matriks Perbandingan");

        pBarisAlternatif.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pBarisAlternatifLayout = new javax.swing.GroupLayout(pBarisAlternatif);
        pBarisAlternatif.setLayout(pBarisAlternatifLayout);
        pBarisAlternatifLayout.setHorizontalGroup(
            pBarisAlternatifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        pBarisAlternatifLayout.setVerticalGroup(
            pBarisAlternatifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        pKolomAlternatif.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pKolomAlternatifLayout = new javax.swing.GroupLayout(pKolomAlternatif);
        pKolomAlternatif.setLayout(pKolomAlternatifLayout);
        pKolomAlternatifLayout.setHorizontalGroup(
            pKolomAlternatifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
        );
        pKolomAlternatifLayout.setVerticalGroup(
            pKolomAlternatifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 453, Short.MAX_VALUE)
        );

        pPerbandingan.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout pPerbandinganLayout = new javax.swing.GroupLayout(pPerbandingan);
        pPerbandingan.setLayout(pPerbandinganLayout);
        pPerbandinganLayout.setHorizontalGroup(
            pPerbandinganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        pPerbandinganLayout.setVerticalGroup(
            pPerbandinganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        bHitung.setText("Hitung");
        bHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitungActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Perbandingan Alternatif Sub Kriteria");

        tCR.setEnabled(false);

        jLabel5.setText("CR");

        tCI.setEnabled(false);

        jLabel4.setText("RI");

        jLabel6.setText("CI ");

        tRI1.setEnabled(false);

        tstatusCR.setText("status");

        jLabel7.setText("test");

        jLabelBro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelBro.setText("(C*)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(pKolomAlternatif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pBarisAlternatif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pPerbandingan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tCI, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tRI1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tCR, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(tstatusCR)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel7))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(320, 320, 320)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelBro))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(jLabel2)))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pBarisAlternatif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pKolomAlternatif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pPerbandingan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tCI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tRI1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tCR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tstatusCR)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(bHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
     public void hitungPembagian(double nilai1, double nilai2, JTextField textField) {
        double hasil = nilai1 / nilai2;
        textField.setText(String.valueOf(numberFormat.format(hasil)));
    }
     void hitungTotalPK(){
        //          Hitung total perbandingan jumlahAlternatif
            double totalPerbandinganKriteria = 0;
            for (int i = 0; i<jumlahAlternatif; i++) {
                for (int j = 0; j < jumlahAlternatif; j++) {
                         double valueTextField = Double.parseDouble(textFields[j][i].getText());
                         totalPerbandinganKriteria += valueTextField; 
                }
                textFields[jumlahAlternatif][i].setText(String.valueOf(numberFormat.format(totalPerbandinganKriteria)));
                
                totalPerbandinganKriteria = 0;
            }
    }
     void hitungNilaiKriteria(){
        //            Hitung semua nilai jumlahAlternatif sampai sebelum total
            for (int i = 0; i < jumlahAlternatif; i++) {
                for (int j = 0; j < jumlahAlternatif; j++) {
                    Double ambilJText = Double.parseDouble(textFields[j][i].getText());
                    Double ambilTotalPK = Double.parseDouble(textFields[jumlahAlternatif][i].getText());
                    Double hitungNilaiK = ambilJText / ambilTotalPK;
                    textFields2[j][i].setText(String.valueOf(numberFormat.format(hitungNilaiK)));
                }

           }
    }
     void hitungTotNK(){
        //         Hitung nilai total dari nilai jumlahAlternatif
            double totalNilaiKriteria = 0;
            for (int i = 0; i < jumlahAlternatif; i++) {
                for (int j = 0; j < jumlahAlternatif; j++) {
                    double valueTextField = Double.parseDouble(textFields2[i][j].getText());
                    totalNilaiKriteria += valueTextField;
                }
                textFields2[i][jumlahAlternatif].setText(String.valueOf(numberFormat.format(totalNilaiKriteria)));
                totalNilaiKriteria = 0;
           }
    }
    
    void hitungPREVCR(){
        // Hitung Nilai Prioritas, Eigen Value, CI, CR
           double totEigenVal = 0;
           for (int i = 0; i < jumlahAlternatif; i++) {
               double getJumlahNK = Double.parseDouble(textFields2[i][jumlahAlternatif].getText());
               double getTotalPK = Double.parseDouble(textFields[jumlahAlternatif][i].getText());
               double prioritas = getJumlahNK / jumlahAlternatif;
                // Set nilai prioritas masing-masing textfield
               textFields2[i][jumlahAlternatif+1].setText(String.valueOf(numberFormat.format(prioritas)));
                //           Eigen Value
             double eigenValue  = prioritas *  getTotalPK;
              totEigenVal += eigenValue;
//               textFields2[i][jumlahAlternatif+2].setText(String.valueOf(numberFormat.format(eigenValue)));
           }
           
           double consistenyIndex = (totEigenVal-jumlahAlternatif)/(jumlahAlternatif-1);
           tCI.setText(String.valueOf(numberFormat.format(consistenyIndex)));
           double consistencyRatio = consistenyIndex /randomIndex;
           
           DecimalFormat decimalFormat = new DecimalFormat("0.###%");
           String formattedCR = decimalFormat.format(consistencyRatio);
           tCR.setText(formattedCR);
           
           if(consistenyIndex > 0.1 && consistencyRatio > 0.1 ){ 
             tstatusCR.setText("Data judgement harus diperbaiki");
           }else if(consistenyIndex < 0.1 && consistencyRatio > 0.1){
              tstatusCR.setText(" Tidak Konsisten");
           }else if(consistenyIndex < 0.1 && consistencyRatio < 0.1){
              tstatusCR.setText(" Konsisten");
              //Simpan kolom data prioritas
              int colPriority = jumlahAlternatif+1;
//             // delete if exist 
//             String sqlDelete = "DELETE FROM pv_alternatif WHERE id_pemilihan = ?";
//             try{
//                 PreparedStatement st = conn.prepareStatement(sqlDelete);
//                 st.setString(1, jLabelBro.getName());
//                 System.out.println(jLabelBro.getName());
//                 st.executeUpdate();
//             } catch (SQLException ex) {
//                   Logger.getLogger(DataAlternatif.class.getName()).log(Level.SEVERE, null, ex);
//               }
               for (int i = 0; i < jumlahAlternatif; i++) {
                   double eachColPriority = Double.parseDouble(textFields2[i][colPriority].getText());
                    String sql = "INSERT INTO pv_alternatif (id_pemilihan, kode_kriteria,nilai_prioritas) VALUES (?,?,?)";
                  try {
                      PreparedStatement st = conn.prepareStatement(sql);
                      
                      st.setString(1,arrIdPem.get(i));
                      st.setString(2, jLabelBro.getName());
                      st.setString(3, String.valueOf(eachColPriority));
                      st.executeUpdate();
                  } catch (SQLException ex) {
                      Logger.getLogger(DataAlternatif.class.getName()).log(Level.SEVERE, null, ex);
                  }
                   
               }
           }
    }
    public void hitungPembagianMatriks(JTextField[][] textFields) {
    int rows = jumlahAlternatif;
    int cols = jumlahAlternatif;
    
    for (int i = 0; i < rows; i++) {
        for (int j = i + 1; j < cols; j++) {
            double nilai1 = Double.parseDouble(textFields[i][i].getText());
            double nilai2 = Double.parseDouble(textFields[j][i].getText());
            double hasil = nilai1 / nilai2;
            textFields[i][j].setText(String.valueOf(numberFormat.format(hasil)));
            hasil = 1 / hasil;
            textFields[j][i].setText(String.valueOf(numberFormat.format(hasil)));
        }
    }
}
    private void bHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitungActionPerformed
        
        boolean isAllFieldsFilled = true;
            for (int i = 0; i <= jumlahAlternatif-2; i++) {
                for (int j = i+1; j <= jumlahAlternatif-1; j++) {
                    if (textFields[i][j].getText().isEmpty()) {
                        isAllFieldsFilled = false;
                        break;
                    }
                }
            }

            if (!isAllFieldsFilled) {
                JOptionPane.showMessageDialog(null, "Isi semua input dulu");
            }
            
            hitungPembagianMatriks(textFields);
            hitungTotalPK();
            hitungNilaiKriteria();
            hitungTotNK();
            hitungPREVCR();
            jLabel7.setText(jumlahAlternatifID);
            hnka.setModalityType(ModalityType.APPLICATION_MODAL);
            hnka.setVisible(true);
    }//GEN-LAST:event_bHitungActionPerformed

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
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DataAlternatif dialog = new DataAlternatif(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHitung;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabelBro;
    private javax.swing.JPanel pBarisAlternatif;
    private javax.swing.JPanel pKolomAlternatif;
    private javax.swing.JPanel pPerbandingan;
    private javax.swing.JTextField tCI;
    private javax.swing.JTextField tCR;
    private javax.swing.JTextField tRI1;
    private javax.swing.JLabel tstatusCR;
    // End of variables declaration//GEN-END:variables
}
