/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.TarifaTableModel;
import dao.DAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.SolicitacaoTarifa;
import entidade.Usuario;
import util.Utils;

/**
 *
 * @author PC_LENOVO
 */
public class FormTabTarifa extends javax.swing.JFrame {

    DAO d = new DAO("rejud");

    TarifaTableModel tableModel = new TarifaTableModel();

    public FormTabTarifa(java.awt.Frame parent, boolean modal) {
        super();

        Utils.setPropriedades();
        initComponents();

        URL caminhoIcone = getClass().getResource("/imagens/banco do brasil.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

        JTTarifa.setModel(tableModel);

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTTarifa = new javax.swing.JTable();
        lblUsuario = new javax.swing.JLabel();
        txtNpj = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        JTTarifa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        JTTarifa.setGridColor(new java.awt.Color(153, 153, 255));
        JTTarifa.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                JTTarifaMouseMoved(evt);
            }
        });
        JTTarifa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTTarifaMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JTTarifaMouseExited(evt);
            }
        });
        JTTarifa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTTarifaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(JTTarifa);

        lblUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtNpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNpjActionPerformed(evt);
            }
        });
        txtNpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNpjKeyPressed(evt);
            }
        });

        jLabel2.setText("Consultar NPJ:");

        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Excluir Linha");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addGap(89, 89, 89))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNpj, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1137, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuario)
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNpj, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, txtNpj});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        Usuario user = new Usuario();
        lblUsuario.setText(user.getMatricula() + "-" + user.getNomeUsuario());
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        JTTarifa.setAutoResizeMode(JTTarifa.AUTO_RESIZE_OFF);
        JTTarifa.getColumnModel().getColumn(0).setPreferredWidth(50);
        JTTarifa.getColumnModel().getColumn(1).setPreferredWidth(150);
        JTTarifa.getColumnModel().getColumn(2).setPreferredWidth(50);           //var
        JTTarifa.getColumnModel().getColumn(3).setPreferredWidth(100);
        JTTarifa.getColumnModel().getColumn(4).setPreferredWidth(150);
        JTTarifa.getColumnModel().getColumn(5).setPreferredWidth(100);          //data credito
        JTTarifa.getColumnModel().getColumn(6).setPreferredWidth(100);
        JTTarifa.getColumnModel().getColumn(7).setPreferredWidth(200);          //beneficiario
        JTTarifa.getColumnModel().getColumn(8).setPreferredWidth(300);
        JTTarifa.getColumnModel().getColumn(9).setPreferredWidth(300);
        JTTarifa.getColumnModel().getColumn(10).setPreferredWidth(300);
        JTTarifa.getColumnModel().getColumn(11).setPreferredWidth(250);         //obs tarifa
        JTTarifa.getColumnModel().getColumn(12).setPreferredWidth(300);
        JTTarifa.getColumnModel().getColumn(13).setPreferredWidth(100);
        JTTarifa.getColumnModel().getColumn(14).setPreferredWidth(100);
        JTTarifa.getColumnModel().getColumn(15).setPreferredWidth(300);
        JTTarifa.getColumnModel().getColumn(16).setPreferredWidth(100);
        JTTarifa.getColumnModel().getColumn(17).setPreferredWidth(100);
        JTTarifa.getColumnModel().getColumn(18).setPreferredWidth(150);
        

        try {
            List<SolicitacaoTarifa> lista = d.readALLTarifa();
            for (SolicitacaoTarifa s : lista) {

                tableModel.addRow(s);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }


    }//GEN-LAST:event_formWindowOpened

    private void JTTarifaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTTarifaMouseClicked
        tableModel.isCellEditable(JTTarifa.getSelectedRow(), JTTarifa.getSelectedColumn());
        tableModel.setValueAt(tableModel.getValueAt(JTTarifa.getSelectedRow(), JTTarifa.getSelectedColumn()), JTTarifa.getSelectedRow(), JTTarifa.getSelectedColumn());

    }//GEN-LAST:event_JTTarifaMouseClicked

    private void JTTarifaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTTarifaMouseMoved

    }//GEN-LAST:event_JTTarifaMouseMoved

    private void JTTarifaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTTarifaMouseExited

    }//GEN-LAST:event_JTTarifaMouseExited

    private void txtNpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNpjKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            JTTarifa.setAutoResizeMode(JTTarifa.AUTO_RESIZE_OFF);
            JTTarifa.getColumnModel().getColumn(0).setPreferredWidth(50);

            JTTarifa.getColumnModel().getColumn(1).setPreferredWidth(150);
            JTTarifa.getColumnModel().getColumn(2).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(3).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(4).setPreferredWidth(150);
            JTTarifa.getColumnModel().getColumn(5).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(6).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(7).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(8).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(9).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(10).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(11).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(12).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(13).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(14).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(15).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(16).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(17).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(18).setPreferredWidth(300);
          
            String npj = txtNpj.getText();
            int qtdLinha = tableModel.getRowCount();

            while (qtdLinha > 0) {
                tableModel.removeRow(0);
                qtdLinha = tableModel.getRowCount();
            }

            try {
                List<SolicitacaoTarifa> lista = d.consultarNPJTarifa(npj);
                for (SolicitacaoTarifa s : lista) {

                    tableModel.addRow(s);

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        }

    }//GEN-LAST:event_txtNpjKeyPressed

    private void JTTarifaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTTarifaKeyPressed
        if (evt.getKeyCode() != evt.VK_ENTER) {
            return;

        }

        if (JTTarifa.getSelectedRow() == -1) {

            return;
        }

        int codigo = (int) tableModel.getValueAt(JTTarifa.getSelectedRow(), 0);
       String nomeColuna = tableModel.getColumnName(JTTarifa.getSelectedColumn());
       String valorCelula = tableModel.getValueAt(JTTarifa.getSelectedRow(), JTTarifa.getSelectedColumn()).toString();
        try {
            d.editar("tb_tarifa", nomeColuna, valorCelula, codigo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro" + "  " + ex);
        }

    }//GEN-LAST:event_JTTarifaKeyPressed

    private void txtNpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNpjActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNpjActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        JTTarifa.setAutoResizeMode(JTTarifa.AUTO_RESIZE_OFF);
//        JTTarifa.getColumnModel().getColumn(0).setPreferredWidth(50);
//
//        JTTarifa.getColumnModel().getColumn(1).setPreferredWidth(150);
//        JTTarifa.getColumnModel().getColumn(2).setPreferredWidth(200);
//        JTTarifa.getColumnModel().getColumn(3).setPreferredWidth(200);
//        JTTarifa.getColumnModel().getColumn(4).setPreferredWidth(150);
//        JTTarifa.getColumnModel().getColumn(5).setPreferredWidth(200);
//        JTTarifa.getColumnModel().getColumn(6).setPreferredWidth(200);
//        JTTarifa.getColumnModel().getColumn(7).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(8).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(9).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(10).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(11).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(12).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(13).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(14).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(15).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(16).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(17).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(18).setPreferredWidth(100);
//        JTTarifa.getColumnModel().getColumn(19).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(20).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(21).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(22).setPreferredWidth(300);
//        JTTarifa.getColumnModel().getColumn(23).setPreferredWidth(300);
//
//        String npj = txtNpj.getText();
//        int qtdLinha = tableModel.getRowCount();
//
//        while (qtdLinha > 0) {
//            tableModel.removeRow(0);
//            qtdLinha = tableModel.getRowCount();
//        }
//
//        try {
//            List<SolicitacaoLevantamento> lista = d.consultarNPJ(npj);
//            for (SolicitacaoLevantamento s : lista) {
//
//                tableModel.addRow(s);
//
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        JTTarifa.setAutoResizeMode(JTTarifa.AUTO_RESIZE_OFF);
            JTTarifa.getColumnModel().getColumn(0).setPreferredWidth(50);

            JTTarifa.getColumnModel().getColumn(1).setPreferredWidth(150);
            JTTarifa.getColumnModel().getColumn(2).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(3).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(4).setPreferredWidth(150);
            JTTarifa.getColumnModel().getColumn(5).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(6).setPreferredWidth(200);
            JTTarifa.getColumnModel().getColumn(7).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(8).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(9).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(10).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(11).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(12).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(13).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(14).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(15).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(16).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(17).setPreferredWidth(300);
            JTTarifa.getColumnModel().getColumn(18).setPreferredWidth(300);
        
       

        int qtdLinha = tableModel.getRowCount();

        while (qtdLinha > 0) {
            tableModel.removeRow(0);
            qtdLinha = tableModel.getRowCount();
        }

        try {
            List<SolicitacaoTarifa> lista = d.readALLTarifa();
            for (SolicitacaoTarifa s : lista) {

                tableModel.addRow(s);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
Usuario user = new Usuario();
        if (JTTarifa.getSelectedRow() != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Tem certeza de que deseja excluir o registro selecionado?",
                    "Exclusão de solicitação de Tarifa de  " + user.getMatricula(), JOptionPane.YES_NO_OPTION);

            if (i == JOptionPane.YES_OPTION) {
                int codigo = (int) tableModel.getValueAt(JTTarifa.getSelectedRow(), 0);
                try {
                    d.removeRegistro("tb_tarifa",codigo);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao exlcuir registro no banco de dados - " + ex);
                }
                tableModel.removeRow(JTTarifa.getSelectedRow());

            } else {
                return;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma linha selecionada!!");
        }


    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(FormTabTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTabTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTabTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTabTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormTabTarifa dialog = new FormTabTarifa(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable JTTarifa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtNpj;
    // End of variables declaration//GEN-END:variables
}
