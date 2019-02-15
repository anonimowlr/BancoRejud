
package view;

import dao.DesconciliacaoDAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import entidade.Desconciliacao;
import rotinasWeb.AtualizacaoWeb;
import util.Autenticar;
import util.Utils;

/**
 *
 * @author suporte
 */
public class FormAtualizacao extends javax.swing.JFrame {

    
    public FormAtualizacao() {
        setPropriedades();
        initComponents();

        URL caminhoIcone = getClass().getResource("/imagens/banco do brasil.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

    }

    private void setPropriedades() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        txtArea = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblTitulo, txtArea});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnIniciar)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblTitulo, txtArea});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        
        this.setTitle("ATUALIZAÇÃO BASE WEB DJO");
        
        Thread thread = new Thread() {

            @Override
            public void run() {      
                
                Autenticar a = new Autenticar();
                a.autenticaUser();
                
                FormAtualizacao bar = new FormAtualizacao();
                DesconciliacaoDAO desconciliacaoDAO = new DesconciliacaoDAO("rejud");
                AtualizacaoWeb atualizacaoWeb = new AtualizacaoWeb("rejud");

                lblTitulo.setText("Efetuando atualização na base da ferramenta Web DJO - Início: " + Utils.getDataHoraAtualMysql());
                btnIniciar.setEnabled(false);

                List<Desconciliacao> descREJUD = desconciliacaoDAO.consultaTbTemp("tb_temporaria_desconciliacao_djo_paj");
                int qtdRejud = descREJUD.size();
//                    List<Desconciliacao> descRETAB = desconciliacaoDAO.consultaTbTemp("tb_temporaria_desconciliacao_djo_paj", 9996);
//                    int qtdRetab = descRETAB.size();

                int k = JOptionPane.showConfirmDialog(null, "Registros web DJO - REJUD: " + qtdRejud
                        //                            + "\n\nRegistros web DJO - RETAB: " + qtdRetab
                        + "\n\nConfirma os dados antes de realizar a atualização \n\nAtualizar base web DJO?",
                        "Atualizar base web DJO", JOptionPane.YES_NO_OPTION);

                if (k == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }

//                    desconciliacaoDAO.atualizacaoDiaria();
                atualizacaoWeb.atualizacaoDiaria(txtArea);

                                
                txtArea.setText("Efetuando atualização do histórico de tratamento e gerando volumetria  dos tratamentos automáticos..aguarde!!");
//                    desconciliacaoDAO.buscarRegistroTratatoAutomatico();

                atualizacaoWeb.atualizacaoOnline();



                btnIniciar.setEnabled(true);
                bar.setVisible(false);

                JOptionPane.showMessageDialog(null, "Fim da atualizaçao:" + Utils.getDataHoraAtualMysql());
                System.exit(0);

            }

        };

        thread.start();

    }//GEN-LAST:event_btnIniciarActionPerformed

    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    
        System.exit(0);

    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("Convert2Lambda")
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormAtualizacao().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel txtArea;
    // End of variables declaration//GEN-END:variables

    
}
