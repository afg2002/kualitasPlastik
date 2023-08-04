/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tampilan;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import helper.koneksi;

/**
 *
 * @author afgha
 */
public final class DataPerbandingan extends javax.swing.JDialog {
   private final Connection conn = new koneksi().getConnection();
   // Untuk Matriks Perbandingan
   JTextField[][] textFields = new JTextField[6][5];
   
   
   // Untuk Matriks Nilai Kriteria;
   JTextField[][] textFields2 = new JTextField[8][8];
    DecimalFormat numberFormat = new DecimalFormat("0.000");
   int kriteria = 0;
   double randomIndex = 0;
   int leftPadding = 0;
   int rightPadding = 0;
   DataHasilNilaiKriteria hnk = new DataHasilNilaiKriteria(null, true);
//   Random Indeks
   
   // Fungsi untuk menginisialisasi variabel kriteria berdasarkan data dalam tabel kriteria
    private void initKriteria() {
        String sql = "SELECT COUNT(*) AS jumlah_kriteria FROM kriteria";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                kriteria = res.getInt("jumlah_kriteria");
                System.out.println(kriteria);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengambil data kriteria!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
       switch (kriteria) {
           case 2:
               leftPadding = 70;
               rightPadding = 110;
               break;
           case 3:
               leftPadding = 40;
               rightPadding = 80;
               break;
           case 4:
               leftPadding = 20;
               rightPadding = 60;
               break;
           default:
               // Untuk jumlah label lebih dari 4, Anda bisa menyesuaikan dengan jarak yang sesuai
               leftPadding = 10;
               rightPadding = 50;
               break;
       }
    }
   
   void setRandomIndex(){
       switch (kriteria) {
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
   
void disableMatriks(){
       for (int i = 0; i < kriteria; i++) {
           textFields[i][i].setText("1");
           textFields[i][i].setEnabled(false);
           textFields[i][i].setForeground(Color.red);
           textFields[kriteria][i].setEnabled(false);
       }
       
       
        int n = kriteria-1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                textFields[i][j].setEnabled(false);
            }
        }
}
   
   void getNamaKriteria(){
       
       try{
           String sql  = "select * from kriteria";
           PreparedStatement st = conn.prepareStatement(sql);
           ResultSet rs = st.executeQuery();
           kolomMPK.setPreferredSize(new Dimension(431, 68));
           kolomMPK.setLayout(new BoxLayout(kolomMPK, BoxLayout.X_AXIS));
           barisMPK.setLayout(new BoxLayout(barisMPK,BoxLayout.Y_AXIS)); 
           barisMPK.setPreferredSize(new Dimension(60, 360)); 
           pMPK.setLayout(new GridLayout(kriteria+1,kriteria+1));
           pMPK.setPreferredSize(new Dimension(431, 360));
           panelSubKriteria.setPreferredSize(new Dimension(453, 30));
           panelSubKriteria.setLayout(new BoxLayout(panelSubKriteria, BoxLayout.X_AXIS));;
           
            // Untuk matriks perbaninan kriteria
            for (int i = 0; i < kriteria+1; i++) {
                for (int j = 0; j < kriteria; j++) {
                    textFields[i][j] = new JTextField();
                    pMPK.add(textFields[i][j]);
                }
            }
            
           while(rs.next()){
               String a  = rs.getString("nama_kriteria");
               String b = rs.getString("kode_kriteria");
               JLabel label = new JLabel(a);
               JLabel label2 = new JLabel(a);
               JLabel label3 = new JLabel(a);
               JLabel label4 = new JLabel(a);
               JButton button = new JButton(a);
               button.setName(b);
               
               
               button.setEnabled(false);
               label.setBorder(new EmptyBorder(0,leftPadding,0,rightPadding));
               
               kolomMPK.add(label);
                // Mengatur margin atau jarak pada JLabel
                label2.setBorder(new EmptyBorder(15, 0, 57, 0)); // Mengatur jarak  piksel di atas, kiri, bawah, dan kanan
                
               barisMPK.add(label2);
//               jPanel4.add(label3);
               label4.setBorder(new EmptyBorder(30,0,70,0));
               hnk.barisNK.add(label4);
               panelSubKriteria.add(button);
               hnk.KolomNK.add(label3);
           }
           
           JLabel label4 = new JLabel("Jumlah");
           JLabel label5 = new JLabel("Prioritas");
           JLabel label6 = new JLabel("Eigen Value");
           JLabel label7 = new JLabel("Total");
           label4.setBorder(new EmptyBorder(0, 0, 0, 50));
           barisMPK.add(label7);
           hnk.KolomNK.add(label4);
           hnk.KolomNK.add(label5);
           hnk.KolomNK.add(label6);
       } catch (SQLException ex) {
           Logger.getLogger(DataPerbandingan.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   //Init Set Nilai Kriteria
   void setNilaiKriteria(){
       double matriks[][] = {
            {1.0000, 3.0000,0.3333},
            {0.3333,1.0000,0.2000},
            {3.0000,5.0000,1.0000},
        };
       
       for (int row = 0; row < kriteria; row++) {
            for (int col = 0; col < kriteria; col++) {
                textFields[row][col].setText(Double.toString(matriks[row][col]));
            }
        }
   }
   
   void getNilaiKriteria(){
       hnk.KolomNK.setLayout(new GridLayout(1,kriteria+2));
       hnk.KolomNK.setPreferredSize(new Dimension(750, 50));
       hnk.barisNK.setLayout(new BoxLayout(hnk.barisNK,BoxLayout.Y_AXIS));
       hnk.barisNK.setPreferredSize(new Dimension(120, 450));
       hnk.pNK.setPreferredSize(new Dimension(750, 450));
       hnk.pNK.setLayout(new GridLayout(kriteria,kriteria+3));
       //untuk matris nilai kriteria
            for (int i = 0; i < kriteria; i++) {
                for (int j = 0; j < kriteria+3; j++) {
                    textFields2[i][j] = new JTextField();
                    textFields2[i][j].setEnabled(false);
//                    textFields2[i][j].setPreferredSize(new Dimension(30, 30));
                    hnk.pNK.add(textFields2[i][j]);
                }
            }

   }
   
    /**
     * Creates new form dataperbandingan
     * @param parent
     * @param modal
     */
    public DataPerbandingan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initKriteria();
        // Buat table dan jlabel untuk perbandingan kriteria
        getNamaKriteria();
        
        // Untuk disable matriks
        disableMatriks();
        setNilaiKriteria();
       
        
        //Buat table dan jlabel untuk penilaian kriteria
        getNilaiKriteria();
        tCI.setPreferredSize(new Dimension(64, 22)); //[64, 22]
        tRI.setPreferredSize(new Dimension(64, 22)); //[64, 22]
        tCR.setPreferredSize(new Dimension(64, 22)); //[64, 22]
        setRandomIndex();
        tRI.setText(String.valueOf(randomIndex));
        tstatusCR.setPreferredSize(new Dimension(236, 31)); //[64, 22]
        tstatusCR.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kolomMPK = new javax.swing.JPanel();
        barisMPK = new javax.swing.JPanel();
        pMPK = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bHitung = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tCI = new javax.swing.JTextField();
        tRI = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tCR = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tstatusCR = new javax.swing.JLabel();
        panelSubKriteria = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        kolomMPK.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout kolomMPKLayout = new javax.swing.GroupLayout(kolomMPK);
        kolomMPK.setLayout(kolomMPKLayout);
        kolomMPKLayout.setHorizontalGroup(
            kolomMPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        kolomMPKLayout.setVerticalGroup(
            kolomMPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        barisMPK.setBackground(new java.awt.Color(255, 255, 255));
        barisMPK.setPreferredSize(new java.awt.Dimension(90, 340));

        javax.swing.GroupLayout barisMPKLayout = new javax.swing.GroupLayout(barisMPK);
        barisMPK.setLayout(barisMPKLayout);
        barisMPKLayout.setHorizontalGroup(
            barisMPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        barisMPKLayout.setVerticalGroup(
            barisMPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        pMPK.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pMPKLayout = new javax.swing.GroupLayout(pMPK);
        pMPK.setLayout(pMPKLayout);
        pMPKLayout.setHorizontalGroup(
            pMPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        pMPKLayout.setVerticalGroup(
            pMPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Data Perbandingan");

        bHitung.setText("Hitung");
        bHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitungActionPerformed(evt);
            }
        });

        jLabel3.setText("CI ");

        tCI.setEnabled(false);

        tRI.setEnabled(false);

        jLabel4.setText("RI");

        tCR.setEnabled(false);

        jLabel5.setText("CR");

        tstatusCR.setBackground(new java.awt.Color(255, 255, 255));
        tstatusCR.setOpaque(true);

        panelSubKriteria.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelSubKriteriaLayout = new javax.swing.GroupLayout(panelSubKriteria);
        panelSubKriteria.setLayout(panelSubKriteriaLayout);
        panelSubKriteriaLayout.setHorizontalGroup(
            panelSubKriteriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 453, Short.MAX_VALUE)
        );
        panelSubKriteriaLayout.setVerticalGroup(
            panelSubKriteriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Data Perbandingan Alternatif dan Kriteria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(barisMPK, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kolomMPK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pMPK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(230, 230, 230))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelSubKriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tCI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tRI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(tstatusCR, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(108, 108, 108)))
                .addGap(89, 89, 89))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomMPK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(barisMPK, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(pMPK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tCI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tRI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tCR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tstatusCR, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelSubKriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void hitungPembagianMatriks(JTextField[][] textFields) {
    int rows = kriteria;
    int cols = kriteria;
    
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
    
    private void deleteBeforeHitung(){
        // delete if exist 
             String sqlDelete = "DELETE FROM pv_alternatif";
             try{
                 PreparedStatement st = conn.prepareStatement(sqlDelete);
                 st.executeUpdate();
             } catch (SQLException ex) {
                   Logger.getLogger(DataAlternatif.class.getName()).log(Level.SEVERE, null, ex);
               }
        
    }
    private void bHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitungActionPerformed

        try{
            boolean isAllFieldsFilled = true;
            for (int i = 0; i <= kriteria-2; i++) {
                for (int j = i+1; j <= kriteria-1; j++) {
                    if (textFields[i][j].getText().isEmpty()) {
                        isAllFieldsFilled = false;
                        break;
                    }
                }
            }

            if (!isAllFieldsFilled) {
                JOptionPane.showMessageDialog(null, "Isi semua input dulu");
            }

            //            double arrTF[][] = new double[kriteria][kriteria];
            //
            ////            for (int i = 0; i < 5;i++) {
                ////                for (int j = 0; j < 5; j++) {
                    ////                    arrTF[i][j] = Double.parseDouble(textFields[i][j].getText());
                    ////                }
                ////           }

            hitungPembagianMatriks(textFields);
            deleteBeforeHitung();   
            hitungTotalPK();
            hitungNilaiKriteria();
            hitungTotNK();
            hitungPREVCR();
            hnk.setModalityType(ModalityType.APPLICATION_MODAL);
            hnk.setVisible(true);

            ActionListener buttonListener = (ActionEvent e) -> {
                JButton o = (JButton)e.getSource();
                String name1 = o.getName();
                String textButton = o.getText();
                DataAlternatif dad = new DataAlternatif(null,true);
                // Hapus action listener dari button setelah modal dialog ditampilkan
//                for (ActionListener listener : o.getActionListeners()) {
//                    o.removeActionListener(listener);
//                }
                dad.jLabel1.setText("Perbandingan alternatif terhadap "+textButton);
                dad.jLabelBro.setText("(" + name1 + ")");
                dad.jLabelBro.setName(name1);
                dad.jumlahAlternatifID = name1;
                double matriksC1[][] = {
                    {1.0, 1.0, 1.0},
                    {1.0, 1.0, 1.0},
                    {1.0, 1.0, 1.0},
                };      
                
                double matriksC2[][] = {
                    {1.0,2.0,1.0},
                    {0.5,1,0.5},
                    {1.0,2.0,1.0},
                };      
                
                double matriksC3[][] = {
                    {1.0,1.0,2.0},
                    {1.0,1.0,2.0},
                    {0.5,0.5,1.0},
                }; 
                
                double matriks[][] = null;
                String label = dad.jLabelBro.getName();
                System.out.println(label);
                if(null != label)switch (label) {
                    case "C1":
                        matriks = matriksC1;
                        break;
                    case "C2":
                        matriks = matriksC2;
                        break;
                    case "C3":
                        matriks = matriksC3;
                        break;
                    
                    default:
                        break;
                }       for (int row = 0; row < kriteria; row++) {
                    for (int col = 0; col < kriteria; col++) {
//                System.out.println(row+col);
                    dad.textFields[row][col].setText(Double.toString(matriks[row][col]));
                    }
                }       dad.setVisible(true);
            };

            // Loop untuk menambahkan action listener ke setiap button di panelSubKriteria
            Component[] components = panelSubKriteria.getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setEnabled(true);
                    button.addActionListener(buttonListener);
                }
            }

        }catch(NumberFormatException e){
            System.out.println("Ada input yang masih kosong");
        }
    }//GEN-LAST:event_bHitungActionPerformed
//    public void hitungPembagian(double nilai1, double nilai2, JTextField textField) {
//        double hasil = nilai1 / nilai2;
//        textField.setText(String.valueOf(numberFormat.format(hasil)));
//    }
    void hitungTotalPK(){
        //          Hitung total perbandingan kriteria
            double totalPerbandinganKriteria = 0;
            for (int i = 0; i<kriteria; i++) {
                for (int j = 0; j < kriteria; j++) {
                         double valueTextField = Double.parseDouble(textFields[j][i].getText());
                         totalPerbandinganKriteria += valueTextField; 
                }
                textFields[kriteria][i].setText(String.valueOf(numberFormat.format(totalPerbandinganKriteria)));
                
                totalPerbandinganKriteria = 0;
            }
    }
    
    void hitungNilaiKriteria(){
        //            Hitung semua nilai kriteria sampai sebelum total
            for (int i = 0; i < kriteria; i++) {
                for (int j = 0; j < kriteria; j++) {
                    Double ambilJText = Double.parseDouble(textFields[j][i].getText());
                    Double ambilTotalPK = Double.parseDouble(textFields[kriteria][i].getText());
                    Double hitungNilaiK = ambilJText / ambilTotalPK;
                    textFields2[j][i].setText(String.valueOf(numberFormat.format(hitungNilaiK)));
                }

           }
    }
    
    void hitungTotNK(){
        //         Hitung nilai total dari nilai kriteria
            double totalNilaiKriteria = 0;
            for (int i = 0; i < kriteria; i++) {
                for (int j = 0; j < kriteria; j++) {
                    double valueTextField = Double.parseDouble(textFields2[i][j].getText());
                    totalNilaiKriteria += valueTextField;
                }
                textFields2[i][kriteria].setText(String.valueOf(numberFormat.format(totalNilaiKriteria)));
                totalNilaiKriteria = 0;
           }
    }
    
    void hitungPREVCR(){
        // Hitung Nilai Prioritas, Eigen Value, CI, CR
           double totEigenVal = 0;
           for (int i = 0; i < kriteria; i++) {
               double getJumlahNK = Double.parseDouble(textFields2[i][kriteria].getText());
               double getTotalPK = Double.parseDouble(textFields[kriteria][i].getText());
               double prioritas = getJumlahNK / kriteria;
                // Set nilai prioritas masing-masing textfield
               textFields2[i][kriteria+1].setText(String.valueOf(numberFormat.format(prioritas)));
                //           Eigen Value
               double eigenValue  = prioritas *  getTotalPK;
               totEigenVal += eigenValue;
               textFields2[i][kriteria+2].setText(String.valueOf(numberFormat.format(eigenValue)));
           }
           
           double consistenyIndex = (totEigenVal-kriteria)/(kriteria-1);
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
              int colPriority = kriteria+1;
               for (int i = 0; i < kriteria; i++) {
                   double eachColPriority = Double.parseDouble(textFields2[i][colPriority].getText());
                   String sql = "INSERT  INTO pv_kriteria (kode_kriteria, nilai_prioritas)VALUES (?,?) ON DUPLICATE KEY UPDATE nilai_prioritas = ? ";
                  try {
                      PreparedStatement st = conn.prepareStatement(sql);
                      int kodeKriteria = i+1;
                      System.out.println(String.valueOf(kodeKriteria) + "," +  String.valueOf(eachColPriority));
                      st.setString(1, "C"+String.valueOf(kodeKriteria));
                      st.setString(2, String.valueOf(eachColPriority));
                      st.setString(3, String.valueOf(eachColPriority));
                      st.executeUpdate();
                  } catch (SQLException ex) {
                      Logger.getLogger(DataPerbandingan.class.getName()).log(Level.SEVERE, null, ex);
                  }
                   
               }
           }
    }
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
            java.util.logging.Logger.getLogger(DataPerbandingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPerbandingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPerbandingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPerbandingan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DataPerbandingan dp = new DataPerbandingan(new javax.swing.JFrame(), true);
                dp.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dp.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHitung;
    private javax.swing.JPanel barisMPK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel kolomMPK;
    private javax.swing.JPanel pMPK;
    private javax.swing.JPanel panelSubKriteria;
    private javax.swing.JTextField tCI;
    private javax.swing.JTextField tCR;
    private javax.swing.JTextField tRI;
    private javax.swing.JLabel tstatusCR;
    // End of variables declaration//GEN-END:variables
}
