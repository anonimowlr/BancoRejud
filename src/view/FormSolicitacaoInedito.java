
package view;

import dao.IneditoDAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import entidade.SolicitacaoInedito;
import entidade.Usuario;
import rotinasPortal.Coletas;
import util.Utils;

/**
 *
 * @author suporte
 */
public class FormSolicitacaoInedito extends javax.swing.JFrame {

    /**
     * Creates new form FormSolicitacaoTarifa
     */
    public FormSolicitacaoInedito() {
        setPropriedades();
        initComponents();
       
    
        
      URL caminhoIcone =getClass().getResource("/imagens/banco do brasil.png");
      Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
       this.setIconImage(iconeTitulo);
    
    
    }
    
     public void setPropriedades() {
        
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
    } 
    
    
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNPJ = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtResgate = new javax.swing.JTextField();
        btnSalvarTarifa = new javax.swing.JButton();
        txtVariacao = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDjo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTipoDestinacao = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Npj:");

        txtNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNPJActionPerformed(evt);
            }
        });
        txtNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNPJKeyTyped(evt);
            }
        });

        jLabel2.setText("Valor do Resgate:");

        txtResgate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtResgateActionPerformed(evt);
            }
        });

        btnSalvarTarifa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSalvarTarifa.setText("Salvar");
        btnSalvarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarTarifaActionPerformed(evt);
            }
        });

        jLabel9.setText("Variação:");

        jLabel12.setText("DJO");

        jLabel3.setText("Tipo de Destinação");

        txtTipoDestinacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Desfavoravel/Transferencia", "Favoravel" }));
        txtTipoDestinacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoDestinacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDjo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtResgate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txtNPJ, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(31, 31, 31)
                                .addComponent(txtVariacao, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTipoDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvarTarifa)
                    .addComponent(txtVariacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtResgate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtDjo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtTipoDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtNPJ, txtResgate, txtVariacao});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNPJActionPerformed

    private void txtResgateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtResgateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtResgateActionPerformed

    private void btnSalvarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarTarifaActionPerformed

        Coletas coletas = new Coletas();
        Usuario user = new Usuario();
        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, nao será possível gravar a solicitação de  tarifa ");
            return;
        }

        if (txtNPJ.getText().equals("") || txtResgate.getText().equals("")  || txtDjo.getText().equals("")   ) {
            JOptionPane.showMessageDialog(this, "Algum campo de preenchimento obrigatório está em branco");
            return;
        }
        
        
        
        
        
        

        IneditoDAO<SolicitacaoInedito> d = new IneditoDAO<>("rejud");
        SolicitacaoInedito inedito = null;
        

        try {

            String npj = txtNPJ.getText().trim();
            int variacaoNpj = (int) txtVariacao.getValue();
            String valorResgateFormulario = txtResgate.getText().trim();
            Double valorResgate =  Utils.converteParaDouble(valorResgateFormulario);
            String contaJudicial = txtDjo.getText().trim();
            String funcionarioResponsavelSolicitacao = user.getMatricula();
            String dataAtual = Utils.getDataAtual();
            Date dataSolicitacao = Utils.formataData(dataAtual);
            String tipoDestinacao =  txtTipoDestinacao.getSelectedItem().toString();
            
            
           inedito= new SolicitacaoInedito(npj, variacaoNpj, valorResgate, contaJudicial, funcionarioResponsavelSolicitacao, dataSolicitacao, tipoDestinacao);
            
            
            
            
            
            
            
            
            
            
            
            
            
            
           
           d.inserirInedito(inedito);
           
           JOptionPane.showMessageDialog(this, "Dados Gravados!!");

            limpar();
           
        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this, ex);
            return;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            return;
        }

        
        


    }//GEN-LAST:event_btnSalvarTarifaActionPerformed

    private void txtNPJKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNPJKeyTyped
       char c  = evt.getKeyChar();
            
            if(c<'0'|| c>'9') evt.consume();
    }//GEN-LAST:event_txtNPJKeyTyped

    private void txtTipoDestinacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoDestinacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoDestinacaoActionPerformed

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
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSolicitacaoInedito().setVisible(true);
            }
        });
    }
    
    
    public void limpar(){
    txtNPJ.setText(null);
    txtResgate.setText(null);
    txtVariacao.setValue(0);
    txtDjo.setText(null);
    
    
    
    
    
    
    
         
    
}
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvarTarifa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtDjo;
    private javax.swing.JTextField txtNPJ;
    private javax.swing.JTextField txtResgate;
    private javax.swing.JComboBox<String> txtTipoDestinacao;
    private javax.swing.JSpinner txtVariacao;
    // End of variables declaration//GEN-END:variables
}
