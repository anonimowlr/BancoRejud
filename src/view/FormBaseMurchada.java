
package view;

import dao.DAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.LevantamentoTableModel;
import entidade.SolicitacaoLevantamento;
import entidade.Usuario;

/**
 *
 * @author PC_LENOVO
 */
public class FormBaseMurchada extends javax.swing.JDialog {
    DAO d = new DAO("rejud");

    LevantamentoTableModel tableModel = new LevantamentoTableModel();

    public FormBaseMurchada(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        JTLevantamento.setModel(tableModel);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTLevantamento = new javax.swing.JTable();
        lblUsuario = new javax.swing.JLabel();
        txtNpj = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
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

        JTLevantamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        JTLevantamento.setGridColor(new java.awt.Color(153, 153, 255));
        JTLevantamento.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                JTLevantamentoMouseMoved(evt);
            }
        });
        JTLevantamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTLevantamentoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JTLevantamentoMouseExited(evt);
            }
        });
        JTLevantamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTLevantamentoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(JTLevantamento);

        lblUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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
                        .addGap(33, 33, 33)
                        .addComponent(jButton1)
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
                    .addComponent(txtNpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        Usuario user = new Usuario();
        lblUsuario.setText(user.getMatricula() + "-" + user.getNomeUsuario());
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        JTLevantamento.setAutoResizeMode(JTLevantamento.AUTO_RESIZE_OFF);
        JTLevantamento.getColumnModel().getColumn(0).setPreferredWidth(50);

        JTLevantamento.getColumnModel().getColumn(1).setPreferredWidth(150);
        JTLevantamento.getColumnModel().getColumn(2).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(3).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(4).setPreferredWidth(150);
        JTLevantamento.getColumnModel().getColumn(5).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(6).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(7).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(8).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(9).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(10).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(11).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(12).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(13).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(14).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(15).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(16).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(17).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(18).setPreferredWidth(100);
        JTLevantamento.getColumnModel().getColumn(19).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(20).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(21).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(22).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(23).setPreferredWidth(300);

        try {
            List<SolicitacaoLevantamento> lista = d.baseMurchada();
            for (SolicitacaoLevantamento s : lista) {

                tableModel.addRow(s);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }


    }//GEN-LAST:event_formWindowOpened

    private void JTLevantamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTLevantamentoMouseClicked
        tableModel.isCellEditable(JTLevantamento.getSelectedRow(), JTLevantamento.getSelectedColumn());
        tableModel.setValueAt(tableModel.getValueAt(JTLevantamento.getSelectedRow(), JTLevantamento.getSelectedColumn()), JTLevantamento.getSelectedRow(), JTLevantamento.getSelectedColumn());

    }//GEN-LAST:event_JTLevantamentoMouseClicked

    private void JTLevantamentoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTLevantamentoMouseMoved
      
    }//GEN-LAST:event_JTLevantamentoMouseMoved

    private void JTLevantamentoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTLevantamentoMouseExited
     
    }//GEN-LAST:event_JTLevantamentoMouseExited

    private void txtNpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNpjKeyPressed
       
    }//GEN-LAST:event_txtNpjKeyPressed

    private void JTLevantamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTLevantamentoKeyPressed
      if (evt.getKeyCode() != evt.VK_ENTER) { 
            return;
            
        }  
        
        
        
        if (JTLevantamento.getSelectedRow() == -1) {

            return;
        }

        int codigo = (int) tableModel.getValueAt(JTLevantamento.getSelectedRow(), 0);
        String npj = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 1).toString();
        int variacao =(int) tableModel.getValueAt(JTLevantamento.getSelectedRow(), 2);
        String valorSolicitacao = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 3).toString();
        String saldo = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 4).toString();
        String oficio = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 5).toString();
        String dataOficio = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 6).toString();
        String obs = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 7).toString();
        String obsContabilizacao = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 8).toString();
        String obsDespacho = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 9).toString();
        String obsIncluirParte = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 10).toString();
        String enderecoDoc1 = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 11).toString();
        String enderecoDoc2 = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 12).toString();
        String beneficiario = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 13).toString();
        String cpfBeneficiario = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 14).toString();
        String tipoLevantamento = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 18).toString();
        String levantador = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 19).toString();
        String finalidade = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 20).toString();
        String especificacao = tableModel.getValueAt(JTLevantamento.getSelectedRow(), 21).toString();

        SolicitacaoLevantamento s = new SolicitacaoLevantamento(codigo, npj, variacao, oficio, dataOficio, valorSolicitacao, enderecoDoc1, saldo, obs, enderecoDoc2, levantador, tipoLevantamento, finalidade, especificacao, beneficiario, cpfBeneficiario, obsDespacho, obsContabilizacao, obsIncluirParte);
        
        try {
            d.atualizaSolicitacao(s);
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao atualizar registro" + "  " + ex);
        }
       
    }//GEN-LAST:event_JTLevantamentoKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         JTLevantamento.setAutoResizeMode(JTLevantamento.AUTO_RESIZE_OFF);
        JTLevantamento.getColumnModel().getColumn(0).setPreferredWidth(50);

        JTLevantamento.getColumnModel().getColumn(1).setPreferredWidth(150);
        JTLevantamento.getColumnModel().getColumn(2).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(3).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(4).setPreferredWidth(150);
        JTLevantamento.getColumnModel().getColumn(5).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(6).setPreferredWidth(200);
        JTLevantamento.getColumnModel().getColumn(7).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(8).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(9).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(10).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(11).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(12).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(13).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(14).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(15).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(16).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(17).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(18).setPreferredWidth(100);
        JTLevantamento.getColumnModel().getColumn(19).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(20).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(21).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(22).setPreferredWidth(300);
        JTLevantamento.getColumnModel().getColumn(23).setPreferredWidth(300);
        
        
        
        
        
        String npj  = txtNpj.getText();
        int qtdLinha = tableModel.getRowCount();
        
                while (qtdLinha>0){
                    tableModel.removeRow(0);
                    qtdLinha = tableModel.getRowCount();
                }
                
                
                
           try {
            List<SolicitacaoLevantamento> lista = d.consultarNPJMurchado(npj);
            for (SolicitacaoLevantamento s : lista) {

                tableModel.addRow(s);
                

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

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
            java.util.logging.Logger.getLogger(FormBaseMurchada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBaseMurchada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBaseMurchada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBaseMurchada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormBaseMurchada dialog = new FormBaseMurchada(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable JTLevantamento;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtNpj;
    // End of variables declaration//GEN-END:variables
}
