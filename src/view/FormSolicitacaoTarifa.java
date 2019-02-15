/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import entidade.SolicitacaoTarifa;
import entidade.Usuario;
import rotinasPortal.Coletas;
import util.Utils;

/**
 *
 * @author suporte
 */
public class FormSolicitacaoTarifa extends javax.swing.JFrame {

    /**
     * Creates new form FormSolicitacaoTarifa
     */
    public FormSolicitacaoTarifa() {
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
        txtValorTarifa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEnderecoDocumento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNumeroDocumento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtProtocoloGsv = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtBancoBeneficiario = new javax.swing.JComboBox<>();
        btnIncluirDocumentoTarifa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtAgenciaContabil = new javax.swing.JTextField();
        btnSalvarTarifa = new javax.swing.JButton();
        txtDataDocumento = new javax.swing.JFormattedTextField();
        txtVariacao = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtValorCredito = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDataCredito = new javax.swing.JFormattedTextField();

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

        jLabel2.setText("Valor da tarifa:");

        txtValorTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorTarifaActionPerformed(evt);
            }
        });

        jLabel3.setText("Endereço documento");

        txtEnderecoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoDocumentoActionPerformed(evt);
            }
        });

        jLabel4.setText("Numero do Documento:");

        jLabel5.setText("Data do Documento:");

        jLabel6.setText("Protocolo Gsv:");

        jLabel7.setText("Banco Beneficiario:");

        txtBancoBeneficiario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o Banco Beneficiario", "CAIXA ECONOMICA FEDERAL", "BANRISUL", "BANESTES", "BANPARA", "BANESE", " " }));

        btnIncluirDocumentoTarifa.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnIncluirDocumentoTarifa.setText("Incluir");
        btnIncluirDocumentoTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirDocumentoTarifaActionPerformed(evt);
            }
        });

        jLabel8.setText("Ag Contabil:");

        btnSalvarTarifa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSalvarTarifa.setText("Salvar");
        btnSalvarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarTarifaActionPerformed(evt);
            }
        });

        try {
            txtDataDocumento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel9.setText("Variação:");

        jLabel10.setText("Valor Crédito:");

        jLabel11.setText("Data Crédito:");

        try {
            txtDataCredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txtValorTarifa, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNPJ, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(31, 31, 31)
                                .addComponent(txtVariacao, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtEnderecoDocumento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnIncluirDocumentoTarifa)
                            .addComponent(btnSalvarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDataDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(txtValorCredito)
                            .addComponent(txtDataCredito))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAgenciaContabil, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProtocoloGsv, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBancoBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnIncluirDocumentoTarifa, btnSalvarTarifa});

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
                    .addComponent(txtValorTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIncluirDocumentoTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEnderecoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtDataDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtValorCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtProtocoloGsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtAgenciaContabil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBancoBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnIncluirDocumentoTarifa, btnSalvarTarifa});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAgenciaContabil, txtDataCredito, txtDataDocumento, txtEnderecoDocumento, txtNPJ, txtNumeroDocumento, txtProtocoloGsv, txtValorCredito, txtValorTarifa, txtVariacao});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNPJActionPerformed

    private void txtValorTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorTarifaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorTarifaActionPerformed

    private void txtEnderecoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnderecoDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnderecoDocumentoActionPerformed

    private void btnSalvarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarTarifaActionPerformed

        Coletas coletas = new Coletas();
        Usuario user = new Usuario();
        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, nao será possível gravar a solicitação de  tarifa ");
            return;
        }

        if (txtNPJ.getText().equals("") || txtValorTarifa.getText() == null || txtDataDocumento.getText().equals("") || txtNumeroDocumento.getText().equals("") || txtEnderecoDocumento.getText().equals("") || txtAgenciaContabil.getText().equals("")  ||  txtBancoBeneficiario.getSelectedItem().equals("Selecione o Banco Beneficiario") ) {
            JOptionPane.showMessageDialog(this, "Algum campo de preenchimento obrigatório está em branco");
            return;
        }
        
        
        
        
        
        

        DAO<SolicitacaoTarifa> d = new DAO<>("rejud");
        SolicitacaoTarifa t = null;
        int i = JOptionPane.showConfirmDialog(null, "Deseja Salvar o pedido de solicitação de  tarifa?",
                "Solicitação de tarifa", JOptionPane.YES_NO_OPTION);

        if (i == JOptionPane.NO_OPTION) {

            return;
        }

        try {

            String npj = txtNPJ.getText().trim();
            int variacaoNpj = (int) txtVariacao.getValue();
            Integer agenciaContabil = Integer.parseInt(txtAgenciaContabil.getText().trim());
            String numeroDocumento = txtNumeroDocumento.getText().trim();

            String dataTexto = txtDataDocumento.getText().trim();// exemplo de data capturada do formulario "16/08/2004";
            Date dataDocumento = Utils.formataData(dataTexto);

            String enderecoDocumento = txtEnderecoDocumento.getText().trim();
            String protocoloGsv = txtProtocoloGsv.getText().trim();

            String valorTarifa = txtValorTarifa.getText().trim();
            Double valorTarifaTratado = coletas.converteParaDouble(valorTarifa);

            String bancoBeneficiario = (String) txtBancoBeneficiario.getSelectedItem();

            String dataAtual = Utils.getDataAtual();
            Date dataAualFormatada = Utils.formataData(dataAtual);
            
            String matriculaUsuario = user.getMatricula();
            
            String valorCreditoTexto =(txtValorCredito.getText());
            Double valorCredito = coletas.converteParaDouble(valorCreditoTexto);
            
            
            
            
            
             String dataCreditoTexto= txtDataCredito.getText().trim();// exemplo de data capturada do formulario "16/08/2004";
            Date dataCredito = Utils.formataData(dataCreditoTexto);
           
            
            int agenciaCont = txtAgenciaContabil.getText().length();
            
            if (agenciaCont  < 4){
                JOptionPane.showMessageDialog(this, "Agencia Contábil deve ter 4 posições numéricas");
               return;
                
            }
            
            if(valorTarifaTratado>30){
                JOptionPane.showMessageDialog(this, "Atualmente não é permitido solicitar Inclusão de Custo de Tarifa superior a R$ 30,00");
                return;
            }
            
            
            
            
            
           t = new SolicitacaoTarifa(npj,variacaoNpj, valorTarifaTratado, bancoBeneficiario, enderecoDocumento, matriculaUsuario, dataAualFormatada, agenciaContabil, numeroDocumento, dataDocumento, protocoloGsv,valorCredito,dataCredito);

           d.inserirSolicitacaoTarifa(t);
           
        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this, ex);
            return;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            return;
        }

        i = JOptionPane.showConfirmDialog(null, "Dados gravados com sucesso!!..." + "\n" + "Deseja manter a  tela com os dados preenchidos para uma próxima inclusão?",
                "Manter dados preenchidos", JOptionPane.YES_NO_OPTION);

        if (i == JOptionPane.NO_OPTION) {

            limpar();
        }


    }//GEN-LAST:event_btnSalvarTarifaActionPerformed

    private void btnIncluirDocumentoTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirDocumentoTarifaActionPerformed
        JFileChooser arquivo = Utils.getFileChooser();
int returnVal = arquivo.showOpenDialog(this);
if (returnVal == JFileChooser.APPROVE_OPTION) {
     Utils.setLastDir(arquivo.getSelectedFile());
     String arquivoSelecionado =arquivo.getSelectedFile().getAbsolutePath();
             File f = new File (arquivoSelecionado);
            long tamanho =( f.length()/1024)/1024;
            
                    String discoArquivo = arquivoSelecionado.subSequence(0,1).toString();  
                    int comprimento = arquivoSelecionado.length();
                  
                     if(comprimento>99){
                            JOptionPane.showMessageDialog(null, "Nome do arquivo com mais de 99 caracteres");
                            txtEnderecoDocumento.setText("");
                            return;
                        }
                    
                    
                    
                    
                    if(!((discoArquivo.equals("G")) || (discoArquivo.equals("P")))){
                            JOptionPane.showMessageDialog(null, "Documento nao está na rede,não será possível prosseguir!!");
                            txtEnderecoDocumento.setText("");
                            return;
                        }
            
            
            
             
                    if (tamanho>=5){
                        JOptionPane.showMessageDialog(this, "Tamanho máximo de documento para inclusão no Portal jurídico é de 5 MB, este  arquivo tem " + tamanho + " Mega Byte");
                        return;
                    }
            

        
        txtEnderecoDocumento.setText(arquivoSelecionado);
        
      
          
        
   
   
        
         
        
        
  
    }                            
    }//GEN-LAST:event_btnIncluirDocumentoTarifaActionPerformed

    private void txtNPJKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNPJKeyTyped
       char c  = evt.getKeyChar();
            
            if(c<'0'|| c>'9') evt.consume();
    }//GEN-LAST:event_txtNPJKeyTyped

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
                new FormSolicitacaoTarifa().setVisible(true);
            }
        });
    }
    
    
    public void limpar(){
    txtNPJ.setText(null);
    txtValorTarifa.setText(null);
    txtEnderecoDocumento.setText(null);
    txtNumeroDocumento.setText(null);
    txtDataDocumento.setText(null);
    txtProtocoloGsv.setText(null);
    txtAgenciaContabil.setText(null);
    txtBancoBeneficiario.setSelectedIndex(0);
    txtVariacao.setValue(0);
    txtValorCredito.setText(null);
    txtDataCredito.setText(null);
    
    //txtIncluir.setSelectedIndex(0);
    
    
    
    
    
    
         
    
}
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIncluirDocumentoTarifa;
    private javax.swing.JButton btnSalvarTarifa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtAgenciaContabil;
    private javax.swing.JComboBox<String> txtBancoBeneficiario;
    private javax.swing.JFormattedTextField txtDataCredito;
    private javax.swing.JFormattedTextField txtDataDocumento;
    private javax.swing.JTextField txtEnderecoDocumento;
    private javax.swing.JTextField txtNPJ;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtProtocoloGsv;
    private javax.swing.JTextField txtValorCredito;
    private javax.swing.JTextField txtValorTarifa;
    private javax.swing.JSpinner txtVariacao;
    // End of variables declaration//GEN-END:variables
}
